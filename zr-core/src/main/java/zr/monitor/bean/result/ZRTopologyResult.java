package zr.monitor.bean.result;

import java.util.LinkedList;

public class ZRTopologyResult {
	protected String reqId;
	protected LinkedList<ZRTopology> topologys;

	public ZRTopologyResult() {
	}

	public ZRTopologyResult(String reqId, LinkedList<ZRTopology> topologys) {
		this.reqId = reqId;
		this.topologys = topologys;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public LinkedList<ZRTopology> getTopologys() {
		return topologys;
	}

	public void setTopologys(LinkedList<ZRTopology> topologys) {
		this.topologys = topologys;
	}

}
