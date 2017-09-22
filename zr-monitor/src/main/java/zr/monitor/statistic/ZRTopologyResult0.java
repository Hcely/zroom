package zr.monitor.statistic;

import java.util.List;

import zr.monitor.bean.result.ZRTopology;
import zr.monitor.bean.result.ZRTopologyResult;

final class ZRTopologyResult0 extends ZRTopologyResult {

	void set(String reqId, List<ZRTopology> topologys) {
		this.reqId = reqId;
		this.topologys = topologys;
	}

	void reset() {
		this.reqId = null;
		this.topologys = null;
	}

}
