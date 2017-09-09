package zr.monitor.statistic;

import v.ObjBuilder;
import zr.monitor.ZRRequest;
import zr.monitor.ZRTopology;
import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.method.ZRMethod;

class ZRStatistic extends ZRRequestResult implements Cloneable {
	public static final byte TYPE_REQUEST = 1;
	public static final byte TYPE_TOPOLOGY = 2;

	private static final ZRStatistic INSTANCE = new ZRStatistic();

	public static final ObjBuilder<ZRStatistic> BUILDER = new ObjBuilder<ZRStatistic>() {
		@Override
		public ZRStatistic build() {
			return INSTANCE.clone();
		}

		@Override
		public Class<ZRStatistic> getType() {
			return ZRStatistic.class;
		}
	};

	protected byte statisticType;
	protected int id;

	public ZRStatistic() {
	}

	@Override
	protected ZRStatistic clone() {
		try {
			return (ZRStatistic) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	void reset() {
		this.version = null;
		this.methodName = null;
		this.reqId = null;
		this.prevId = null;
		this.silkId = null;
		this.logContent = null;
		this.error = null;
		this.remoteIp = null;
	}

	public void set(ZRRequest zreq, ZRTopology topology, String logContent) {
		ZRMethod method = zreq.getMethod();
		this.statisticType = TYPE_REQUEST;
		this.id = method.getId();
		this.version = method.getVersion();
		this.methodName = method.getMethodName();
		this.startTime = zreq.getStartTime();
		this.take = zreq.getTake();
		this.resultType = zreq.getResultType();
		if (topology != null) {
			this.reqId = topology.getReqId();
			this.prevId = topology.getPrevId();
			this.silkId = topology.getSilkId();
		}
		this.logContent = logContent;
		this.error = zreq.getError();
		this.remoteIp = zreq.getRemoveIp();
	}

	public void set(ZRTopology topology, byte resultType) {
		this.statisticType = TYPE_TOPOLOGY;
		this.version = topology.getVersion();
		this.methodName = topology.getMethodName();
		this.startTime = topology.getStartTime();
		this.take = topology.getTake();
		this.resultType = resultType;
		this.reqId = topology.getReqId();
		this.prevId = topology.getPrevId();
		this.silkId = topology.getSilkId();
	}

}
