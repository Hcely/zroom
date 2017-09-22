package zr.monitor.bean.result;

import java.util.List;

public class ZRTopologyResult {
	protected String reqId;
	protected List<ZRTopology> topologys;

	public ZRTopologyResult() {
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public List<ZRTopology> getTopologys() {
		return topologys;
	}

	public void setTopologys(List<ZRTopology> topologys) {
		this.topologys = topologys;
	}

}
