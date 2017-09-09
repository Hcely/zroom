package zr.monitor;

import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import v.Initializable;
import v.VObject;
import v.common.helper.ParseUtil;
import v.common.unit.VThreadLoop;
import zr.monitor.cluster.ZRClusterServer;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.method.ZRMethod;
import zr.monitor.method.ZRMethodMgr;
import zr.monitor.statistic.ZRStatisticCenter;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ZRCenter implements Initializable, VObject {
	public static final String ZR_REQUEST_ID = "zr-req-id";
	public static final String ZR_REQUEST_SILK_ID = "zr-silk-id";
	public static final String ZR_REQUEST_NUM = "zr-cur-num";

	protected VThreadLoop loop;
	protected ZRInfoMgr infoMgr;
	protected ZRMethodMgr methodMgr;
	protected ZRStatisticCenter statisticCenter;

	protected ZRClusterServer clusterServer;

	protected Set<Method> methods;
	protected Set<ZRRequestFilter> filters;
	protected ZRRequestHandler reqHandler;

	public void setMethods(Set<Method> methods) {
		this.methods = methods;
	}

	public void setFilters(Set<ZRRequestFilter> filters) {
		this.filters = filters;
	}

	public void init() {
		loop = new VThreadLoop();
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDestory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object execute(Object invoker, Method method, String remoteIp, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		ZRMethod zrm = methodMgr.getMethod(method);
		if (zrm == null)
			return reqHandler.executeNoMethod(invoker);
		ZRRequest zreq = ZRContext.getRequest(zrm, remoteIp, request, response);
		try {
			if (zrm.isOpen() && infoMgr.isOpen())
				return handleRequest(invoker, zrm, zreq);
			else
				return handleApiClose(invoker, zreq);
		} finally {
			zreq.reset();
		}
	}

	private final Object handleRequest(final Object invoker, final ZRMethod zrm, final ZRRequest zreq)
			throws Throwable {
		final ZRTopology topology = checkTopology(zrm, zreq.request, zreq.startTime);
		int n = 0;
		final ZRRequestFilter[] filters = zrm.getFilters();
		Throwable errorHr = null;
		boolean execute = true, after = true;
		String logContent = null;
		try {
			if (reqHandler.onBefore(invoker, zreq)) {
				for (int len = filters.length; n < len; ++n)
					if (!filters[n].onBefore(zreq)) {
						execute = after = false;
						break;
					}
			} else
				execute = after = false;
		} catch (Throwable e) {
			execute = after = false;
			zreq.resultType = ZRRequest.RESULT_ERROR;
			zreq.error = e;
			errorHr = handleError(invoker, zreq, filters, n, false);
		}

		if (execute) {
			try {
				reqHandler.execute(invoker, zreq);
			} catch (Throwable e) {
				after = false;
				zreq.resultType = ZRRequest.RESULT_ERROR;
				zreq.error = e;
				errorHr = handleError(invoker, zreq, filters, n, true);
			}
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
		try {
			zreq.end();
			if (topology != null)
				topology.take = zreq.take;
			logContent = reqHandler.onLog(invoker, zreq);
		} catch (Throwable e) {
		}
		statisticCenter.putRequestTask(zreq, topology, logContent);
		try {
			if (errorHr != null)
				throw errorHr;
			return zreq.hresult;
		} finally {
			if (topology != null) {
				HttpServletRequest request = zreq.getRequest();
				request.setAttribute(ZR_REQUEST_ID, topology.reqId);
				request.setAttribute(ZR_REQUEST_SILK_ID, topology.silkId);
				request.setAttribute(ZR_REQUEST_NUM, topology.incNum());
				ZRContext.removeTopology();
			}
		}

	}

	private Throwable handleError(final Object invoker, final ZRRequest zreq, final ZRRequestFilter[] filters,
			final int n, boolean resultError) {
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

	private final Object handleApiClose(final Object invoker, final ZRRequest zreq) throws Throwable {
		reqHandler.onApiClose(invoker, zreq);
		return zreq.hresult;
	}

	private final ZRTopology checkTopology(final ZRMethod zrm, final HttpServletRequest request, final long startTime) {
		String reqId = request.getHeader(ZR_REQUEST_ID);
		if (reqId != null) {
			String prevId = request.getHeader(ZR_REQUEST_SILK_ID);
			String numStr = request.getHeader(ZR_REQUEST_NUM);
			int num = ParseUtil.parse(numStr, Integer.class, 0);
			return ZRContext.getTopology(reqId, prevId, zrm.getMethodName(), zrm.getVersion(), num, startTime);
		} else if ((reqId = (String) request.getAttribute(ZR_REQUEST_ID)) != null) {
			String prevId = (String) request.getAttribute(ZR_REQUEST_SILK_ID);
			int num = (Integer) request.getAttribute(ZR_REQUEST_NUM);
			return ZRContext.getTopology(reqId, prevId, zrm.getMethodName(), zrm.getVersion(), num, startTime);
		} else {
			int topology = zrm.getTopology();
			if (topology > 0 && zrm.incCount() % topology == 0)
				return ZRContext.getTopology(reqId, null, zrm.getMethodName(), zrm.getVersion(), 0, startTime);
		}
		return null;
	}

}
