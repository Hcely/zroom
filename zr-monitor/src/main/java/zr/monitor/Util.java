package zr.monitor;

import zr.monitor.statistic.ZRStatisticCenter;
import zr.monitor.topology.ZRTopologyContext;

class Util extends ZRTopologyContext {
	public static final void setCenter(ZRMonitorCenter zrCenter) {
		ZRTopologyContext.zrCenter = zrCenter;
	}

	public static final void setStatistic(ZRStatisticCenter statisticCenter) {
		ZRTopologyContext.statisticCenter = statisticCenter;
	}
}
