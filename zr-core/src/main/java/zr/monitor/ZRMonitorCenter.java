package zr.monitor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import v.common.helper.ParseUtil;
import v.common.unit.VSimpleStatusObject;
import v.common.unit.thread.VThreadLoop;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.cluster.ZRServerCluster;
import zr.monitor.info.ZRApiInfoBuilder;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.method.ZRMethod;
import zr.monitor.method.ZRMethodListener;
import zr.monitor.method.ZRMethodMgr;
import zr.monitor.statistic.ZRStatistic;
import zr.monitor.statistic.ZRStatisticCenter;
import zr.monitor.statistic.ZRStatisticHandler;
import zr.monitor.util.ZRMonitorUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ZRMonitorCenter extends VSimpleStatusObject {
	public static final String ZR_REQUEST_ID = "zr-req-id";
	public static final String ZR_REQUEST_PREV_ID = "zr-prev-id";
	public static final String ZR_REQUEST_SILK_ID = "zr-silk-id";
	public static final String ZR_REQUEST_HEADER_NODE = "zr-header-node";

	public static final int DEF_STATISTIC_CORE_NUM = 4;

	protected VThreadLoop loop;
	protected ZRInfoMgr infoMgr;
	protected ZRMethodMgr methodMgr;
	protected ZRStatisticCenter statisticCenter;

	protected ZRServerCluster clusterServer;

	protected Set<Method> methods;
	protected Set<ZRRequestFilter> filters;

	protected int workerNum = DEF_STATISTIC_CORE_NUM;
	protected int cacheSize = 1024 * 16;

	protected ZRRequestHandler reqHandler;
	protected ZRApiInfoBuilder apiInfoBuilder;
	protected ZRMethodListener methodListener;
	protected ZRStatisticHandler statisticHandler;

	public void setMethods(Set<Method> methods) {
		this.methods = methods;
	}

	public void setFilters(Set<ZRRequestFilter> filters) {
		this.filters = filters;
	}

	public void setWorkerNum(int workerNum) {
		this.workerNum = workerNum;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setReqHandler(ZRRequestHandler reqHandler) {
		this.reqHandler = reqHandler;
	}

	public void setApiInfoBuilder(ZRApiInfoBuilder apiInfoBuilder) {
		this.apiInfoBuilder = apiInfoBuilder;
	}

	public void setMethodListener(ZRMethodListener methodListener) {
		this.methodListener = methodListener;
	}

	public void setStatisticHandler(ZRStatisticHandler statisticHandler) {
		this.statisticHandler = statisticHandler;
	}

	@Override
	protected void _init0() {
		loop = new VThreadLoop();

		infoMgr = new ZRInfoMgr(apiInfoBuilder);
		methodMgr = new ZRMethodMgr(infoMgr, methodListener);

		statisticCenter = new ZRStatisticCenter(infoMgr, loop, workerNum, cacheSize, statisticHandler);
		clusterServer = new ZRServerCluster(infoMgr, loop);

		methodMgr.addFilters(filters);
		methodMgr.addMethods(methods);

		loop.start();
		statisticCenter.init();
		clusterServer.init();

		ZRContext.zrCenter = this;
		ZRContext.statisticCenter = statisticCenter;
	}

	@Override
	protected void _destory0() {
		statisticCenter.destory();
		clusterServer.destory();
		loop.destory();

		infoMgr.clear();
		methodMgr.clear();
	}

	public ZRMethod getZRMethod(Method method) {
		return methodMgr.getMethod(method);
	}

	public Object execute(final Object invoker, final ZRMethod zrm, final String remoteIp,
			final HttpServletRequest request, final HttpServletResponse response) throws Throwable {
		if (zrm == ZRMethod.NULL || zrm == null)
			return executeNoMethod(invoker, request);
		final ZRRequest zreq = ZRContext.getRequest(zrm, remoteIp, request, response);
		try {
			if (zrm.isOpen() && infoMgr.isOpen())
				return handleRequest(invoker, zrm, zreq);
			else
				return handleApiClose(invoker, zreq);
		} finally {
			zreq.reset();
		}
	}

	public final Object executeNoMethod(final Object invoker, final HttpServletRequest request) throws Throwable {
		final ZRTopologyStack stack = checkTopology(request, System.currentTimeMillis());
		final ZRTopology topology = stack == null ? null : stack.curTopology();
		byte resultStatus = ZRRequest.RESULT_OK;
		try {
			return reqHandler.executeNoMethod(invoker);
		} catch (Throwable e) {
			resultStatus = ZRRequest.RESULT_ERROR;
			throw e;
		} finally {
			if (stack != null) {
				String reqId = stack.reqId();
				stack.finishAndPopTopology(topology, System.currentTimeMillis(), resultStatus);
				if (stack.isEmpty()) {
					LinkedList<ZRTopology> result = stack.finishAndGetResult();
					ZRStatistic statistic = statisticCenter.product();
					statistic.setAsTopology(reqId, result);
					statisticCenter.finishProduct(statistic);
				}
			}
		}
	}

	private final Object handleApiClose(final Object invoker, final ZRRequest zreq) throws Throwable {
		reqHandler.onApiClose(invoker, zreq);
		return zreq.hresult;
	}

	private final Object handleRequest(final Object invoker, final ZRMethod zrm, final ZRRequest zreq)
			throws Throwable {
		final ZRTopologyStack stack = checkTopology(zrm, zreq.request, zreq.startTime);
		final ZRTopology topology = stack == null ? null : stack.curTopology();
		final ZRRequestFilter[] filters = zrm.getFilters();

		int n = 0;
		Throwable errorHr = null;
		boolean execute = true, after = true;

		try {
			if (reqHandler.onBefore(invoker, zreq)) {
				for (int len = filters.length; n < len; ++n)
					if (!filters[n].onBefore(zreq)) {
						execute = false;
						break;
					}
			} else
				execute = false;
		} catch (Throwable e) {
			execute = after = false;
			errorHr = handleError(invoker, zreq, e, filters, n, false);
		}

		if (execute)
			try {
				reqHandler.execute(invoker, zreq);
			} catch (Throwable e) {
				after = false;
				errorHr = handleError(invoker, zreq, e, filters, n, true);
			}

		if (after) {
			for (int i = n; i > 0;)
				try {
					filters[--i].onAfter(zreq);
				} catch (Throwable e) {
				}
			try {
				reqHandler.onAfter(invoker, zreq);
			} catch (Throwable e) {
			}
		}

		finish(invoker, zreq, stack, topology);

		if (errorHr != null)
			throw errorHr;
		return zreq.hresult;
	}

	private final void finish(final Object invoker, final ZRRequest zreq, final ZRTopologyStack stack,
			final ZRTopology topology) {
		long endTime = System.currentTimeMillis();
		zreq.end(endTime);
		List<ZRTopology> topologyResult = null;
		String reqId = null;
		if (stack != null) {
			reqId = stack.reqId();
			stack.finishAndPopTopology(topology, endTime, zreq.resultStatus);
			if (stack.isEmpty())
				topologyResult = stack.finishAndGetResult();
		}
		ZRStatistic statistic = statisticCenter.product();
		statistic.setAsRequest(zreq, reqId, topologyResult);
		try {
			reqHandler.onLog(invoker, zreq, statistic);
		} finally {
			statisticCenter.finishProduct(statistic);
		}
	}

	private final Throwable handleError(final Object invoker, final ZRRequest zreq, Throwable error,
			final ZRRequestFilter[] filters, final int n, boolean resultError) {
		zreq.setError(error);
		for (int i = n; i > 0;)
			try {
				filters[--i].onError(zreq, resultError);
			} catch (Throwable e) {
			}
		try {
			return reqHandler.onError(invoker, zreq, resultError);
		} catch (Throwable e) {
			return null;
		}
	}

	private final ZRTopologyStack checkTopology(final ZRMethod zrm, final HttpServletRequest request,
			final long startTime) {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
		if (stack.isEmpty()) {
			String reqId = request.getHeader(ZR_REQUEST_ID);
			if (reqId != null) {
				String prevId = request.getHeader(ZR_REQUEST_PREV_ID);
				String silkId = request.getHeader(ZR_REQUEST_SILK_ID);
				boolean rootNode = ParseUtil.parseBoolean(request.getHeader(ZR_REQUEST_HEADER_NODE), false);
				return stack.start(reqId, prevId, silkId, zrm.getMethodName(), "0.0.0", startTime, rootNode);
			} else {
				int topology = zrm.getTopology();
				if (topology > 0 && zrm.incCount() % topology == 0)
					return stack.start(ZRMonitorUtil.getReqId(), null, null, zrm.getMethodName(), zrm.getVersion(),
							startTime, true);
			}
		} else {
			stack.addTopology(zrm.getMethodName(), zrm.getVersion(), startTime);
			return stack;
		}
		return null;
	}

	private final ZRTopologyStack checkTopology(final HttpServletRequest request, final long startTime) {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
		if (stack.isEmpty()) {
			String reqId = request.getHeader(ZR_REQUEST_ID);
			if (reqId != null) {
				String prevId = request.getHeader(ZR_REQUEST_PREV_ID);
				String silkId = request.getHeader(ZR_REQUEST_SILK_ID);
				boolean rootNode = ParseUtil.parseBoolean(request.getHeader(ZR_REQUEST_HEADER_NODE), false);
				return stack.start(reqId, prevId, silkId, request.getRequestURI(), "httpApi", startTime, rootNode);
			}
		} else {
			stack.addTopology(request.getRequestURI(), "0.0.0", startTime);
			return stack;
		}
		return null;
	}

}
