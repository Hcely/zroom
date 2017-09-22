package zr.monitor.statistic;

import java.util.List;

import v.ObjBuilder;
import v.common.util.LinkedQueueMap;
import zr.monitor.ZRLogContent;
import zr.monitor.ZRequest;
import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.bean.result.ZRTopologyResult;
import zr.monitor.method.ZRMethod;

public final class ZRStatistic implements ZRLogContent {
	public static final byte TYPE_REQUEST = 1;
	public static final byte TYPE_REQUEST_TOPOLOPY = 2;
	public static final byte TYPE_TOPOLOGY = 3;

	public static final ObjBuilder<ZRStatistic> BUILDER = new ObjBuilder<ZRStatistic>() {
		@Override
		public ZRStatistic build() {
			return new ZRStatistic();
		}

		@Override
		public Class<ZRStatistic> getType() {
			return ZRStatistic.class;
		}
	};
	long idx;
	protected byte statisticType;
	protected int methodId;
	protected final LinkedQueueMap<String, String> flags;
	protected final ZRRequestResult0 requestResult;
	protected final ZRTopologyResult0 topologyResult;

	ZRStatistic() {
		this.requestResult = new ZRRequestResult0();
		this.topologyResult = new ZRTopologyResult0();
		this.flags = new LinkedQueueMap<>();
	}

	void reset() {
		requestResult.reset();
		topologyResult.reset();
		flags.clear();
	}

	public ZRRequestResult getRequestResult() {
		return requestResult;
	}

	public ZRTopologyResult getTopologyResult() {
		return topologyResult;
	}

	public void setAsRequest(ZRequest zreq, String reqId, List<ZRTopology> topologys) {
		ZRMethod method = zreq.getMethod();
		this.methodId = method.getId();
		requestResult.set(reqId, method, zreq);
		if (topologys == null)
			this.statisticType = TYPE_REQUEST;
		else {
			this.statisticType = TYPE_REQUEST_TOPOLOPY;
			this.topologyResult.set(reqId, topologys);
		}
	}

	public void setAsTopology(String reqId, List<ZRTopology> topologys) {
		this.statisticType = TYPE_TOPOLOGY;
		this.topologyResult.set(reqId, topologys);
	}

	@Override
	public void setLogContent(String log) {
		this.requestResult.setLogContent(log);
	}

	@Override
	public void addFlag(String key, String value) {
		flags.put(key, value);
	}

	@Override
	public void clearFlags() {
		flags.clear();
	}

}
