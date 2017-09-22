package zr.monitor.topology;

import java.util.List;

import zr.monitor.ZRMonitorCenter;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.statistic.ZRStatistic;
import zr.monitor.statistic.ZRStatisticCenter;

public class ZRTopologyContext {

	protected static final ThreadLocal<ZRTopologyStack> topologyTL = new ThreadLocal<ZRTopologyStack>() {
		@Override
		protected ZRTopologyStack initialValue() {
			return new ZRTopologyStack();
		}
	};

	public static final ZRTopologyStack curTopologyStack() {
		return topologyTL.get();
	}

	protected static ZRMonitorCenter zrCenter;

	protected static ZRStatisticCenter statisticCenter;

	public static final void putTopology(String reqId, List<ZRTopology> topologys) {
		ZRStatistic statistic = statisticCenter.product();
		statistic.setAsTopology(reqId, topologys);
		statisticCenter.finishProduct(statistic);
	}
}
