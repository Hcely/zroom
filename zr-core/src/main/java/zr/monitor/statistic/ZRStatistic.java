package zr.monitor.statistic;

import java.util.List;

import v.ObjBuilder;
import zr.monitor.ZRRequest;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.method.ZRMethod;

final class ZRStatistic {
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

	protected byte statisticType;
	protected int id;
	protected ZRRequestResult0 requestResult;
	protected ZRTopologyResult0 topologyResult;

	public ZRStatistic() {
		this.requestResult = new ZRRequestResult0();
		this.topologyResult = new ZRTopologyResult0();
	}

	void reset() {
		requestResult.reset();
		topologyResult.reset();
	}

	void set(ZRRequest zreq, String reqId, List<ZRTopology> topologys, String logContent) {
		ZRMethod method = zreq.getMethod();
		this.id = method.getId();
		requestResult.set(reqId, method, zreq, logContent);
		if (topologys == null)
			this.statisticType = TYPE_REQUEST;
		else {
			this.statisticType = TYPE_REQUEST_TOPOLOPY;
			this.topologyResult.set(reqId, topologys);
		}
	}

	public void set(String reqId, List<ZRTopology> topologys) {
		this.statisticType = TYPE_TOPOLOGY;
		this.topologyResult.set(reqId, topologys);
	}

}
