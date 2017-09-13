package zr.monitor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zr.monitor.bean.result.ZRTopology;
import zr.monitor.method.ZRMethod;
import zr.monitor.statistic.ZRStatistic;
import zr.monitor.statistic.ZRStatisticCenter;

public class ZRContext {
	protected static final ThreadLocal<ZRRequest> requestTL = new ThreadLocal<ZRRequest>() {
		@Override
		protected ZRRequest initialValue() {
			return new ZRRequest();
		}
	};

	protected static final ZRRequest getRequest(ZRMethod method, String removeIp, HttpServletRequest request,
			HttpServletResponse response) {
		return requestTL.get().set(method, removeIp, request, response);
	}

	public static final ZRRequest curRequest() {
		return requestTL.get();
	}

	protected static final ThreadLocal<ZRTopologyStack> topologyTL = new ThreadLocal<ZRTopologyStack>() {
		@Override
		protected ZRTopologyStack initialValue() {
			return new ZRTopologyStack();
		}
	};

	public static final ZRTopologyStack curTopologyStack() {
		return topologyTL.get();
	}

	static ZRMonitorCenter zrCenter;

	static ZRStatisticCenter statisticCenter;

	public static final void putTopology(String reqId, List<ZRTopology> topologys) {
		ZRStatistic statistic = statisticCenter.product();
		statistic.setAsTopology(reqId, topologys);
		statisticCenter.finishProduct(statistic);
	}
}
