package zr.monitor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import v.Initializable;
import v.common.unit.VStatusObject;
import v.common.unit.VThreadLoop;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.cluster.ZRServerCluster;
import zr.monitor.info.ZRApiInfoBuilder;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.method.ZRMethod;
import zr.monitor.method.ZRMethodListener;
import zr.monitor.method.ZRMethodMgr;
import zr.monitor.statistic.ZRStatisticCenter;
import zr.monitor.statistic.ZRStatisticHandler;
import zr.monitor.util.ZRMonitorUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ZRCenter extends VStatusObject implements Initializable {
	public static final String ZR_REQUEST_ID = "zr-req-id";
	public static final String ZR_REQUEST_PREV_ID = "zr-prev-id";
	public static final String ZR_REQUEST_SILK_ID = "zr-cur-num";

	protected VThreadLoop loop;
	protected ZRInfoMgr infoMgr;
	protected ZRMethodMgr methodMgr;
	protected ZRStatisticCenter statisticCenter;

	protected ZRServerCluster clusterServer;

	protected Set<Method> methods;
	protected Set<ZRRequestFilter> filters;
	protected int workerNum;
	protected int cacheSize;

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

	@Override
	public void init() {
		if (!initing(this))
			return;
		try {
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
		} finally {
			inited(this);
		}
	}

	@Override
	public void destory() {
		if (!destorying(this))
			return;
		try {

			statisticCenter.destory();
			clusterServer.destory();
			loop.destory();

			infoMgr.clear();
			methodMgr.clear();
		} finally {
			destoryed(this);
		}
	}

	public Object execute(Object invoker, Method method, String remoteIp, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		final ZRMethod zrm = methodMgr.getMethod(method);
		if (zrm == null)
			return handleNoMethod(invoker, request);
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

	private final Object handleNoMethod(Object invoker, HttpServletRequest request) throws Throwable {
		final ZRTopologyStack stack = checkTopology(request, System.currentTimeMillis());
		byte resultStatus = ZRRequest.RESULT_OK;
		try {
			return reqHandler.executeNoMethod(invoker);
		} catch (Throwable e) {
			resultStatus = ZRRequest.RESULT_ERROR;
			throw e;
		} finally {
			if (stack != null) {
				String reqId = stack.reqId();
				ZRTopology topology = stack.finishAndPopTopology(System.currentTimeMillis(), resultStatus);
				if (stack.isEmpty()) {
					LinkedList<ZRTopology> result = stack.finishAndGetResult();
					request.setAttribute(ZR_REQUEST_ID, reqId);
					request.setAttribute(ZR_REQUEST_PREV_ID, topology.getSilkId());
					request.setAttribute(ZR_REQUEST_SILK_ID, topology.nextSilkId());
					statisticCenter.putTopology(reqId, result);
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

		finish(invoker, zreq, stack);

		if (errorHr != null)
			throw errorHr;
		return zreq.hresult;
	}

	private final void finish(final Object invoker, final ZRRequest zreq, final ZRTopologyStack stack) {
		long endTime = System.currentTimeMillis();
		zreq.end(endTime);
		List<ZRTopology> result = null;
		String reqId = null;
		String logContent = null;
		if (stack != null) {
			reqId = stack.reqId();
			ZRTopology topology = stack.finishAndPopTopology(endTime, zreq.resultStatus);
			if (stack.isEmpty()) {
				result = stack.finishAndGetResult();
				HttpServletRequest request = zreq.getRequest();
				request.setAttribute(ZR_REQUEST_ID, reqId);
				request.setAttribute(ZR_REQUEST_PREV_ID, topology.getSilkId());
				request.setAttribute(ZR_REQUEST_SILK_ID, topology.nextSilkId());
			}
		}
		try {
			logContent = reqHandler.onLog(invoker, zreq);
		} finally {
		}
		statisticCenter.putRequestTask(zreq, reqId, result, logContent);
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
		ZRTopologyStack stack = ZRContext.curTopology();
		if (stack.isEmpty()) {
			String reqId = request.getHeader(ZR_REQUEST_ID);
			if (reqId != null) {
				String prevId = request.getHeader(ZR_REQUEST_PREV_ID);
				String silkId = request.getHeader(ZR_REQUEST_SILK_ID);
				return stack.start(reqId, prevId, silkId, zrm.getMethodName(), zrm.getVersion(), startTime);
			} else if ((reqId = (String) request.getAttribute(ZR_REQUEST_ID)) != null) {
				String prevId = (String) request.getAttribute(ZR_REQUEST_PREV_ID);
				String silkId = (String) request.getAttribute(ZR_REQUEST_SILK_ID);
				return stack.start(reqId, prevId, silkId, zrm.getMethodName(), zrm.getVersion(), startTime);
			} else {
				int topology = zrm.getTopology();
				if (topology > 0 && zrm.incCount() % topology == 0)
					return stack.start(ZRMonitorUtil.getReqId(), null, null, zrm.getMethodName(), zrm.getVersion(),
							startTime);
			}
		} else {
			stack.addTopology(zrm.getMethodName(), zrm.getVersion(), startTime);
			return stack;
		}
		return null;
	}

	private final ZRTopologyStack checkTopology(final HttpServletRequest request, final long startTime) {
		ZRTopologyStack stack = ZRContext.curTopology();
		if (stack.isEmpty()) {
			String reqId = request.getHeader(ZR_REQUEST_ID);
			if (reqId != null) {
				String prevId = request.getHeader(ZR_REQUEST_PREV_ID);
				String silkId = request.getHeader(ZR_REQUEST_SILK_ID);
				return stack.start(reqId, prevId, silkId, request.getRequestURI(), "0.0.0", startTime);
			} else if ((reqId = (String) request.getAttribute(ZR_REQUEST_ID)) != null) {
				String prevId = (String) request.getAttribute(ZR_REQUEST_PREV_ID);
				String silkId = (String) request.getAttribute(ZR_REQUEST_SILK_ID);
				return stack.start(reqId, prevId, silkId, request.getRequestURI(), "0.0.0", startTime);
			}
		} else {
			stack.addTopology(request.getRequestURI(), "0.0.0", startTime);
			return stack;
		}
		return null;
	}

}
