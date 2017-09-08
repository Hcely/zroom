package zr.monitor.statistic;

import v.ObjBuilder;

public class ZRStatistic implements Cloneable {
	public static final class Builder implements ObjBuilder<ZRStatistic> {
		private final ZRStatistic instance;

		private Builder(String machineIp, String serverId, String serviceId) {
			instance = new ZRStatistic();
			instance.machineIp = machineIp;
			instance.serverId = serverId;
			instance.serviceId = serviceId;
		}

		@Override
		public ZRStatistic build() {
			return instance.clone();
		}

		@Override
		public Class<ZRStatistic> getType() {
			return ZRStatistic.class;
		}
	}

	public static final Builder builder(String machineIp, String serverId, String serviceId) {
		return new Builder(machineIp, serverId, serviceId);
	}

	protected String machineIp;
	protected String serverId;
	protected String serviceId;

	protected int id;
	protected String version;
	protected String methodName;
	protected long startTime;
	protected long take;
	protected int respType;
	protected String remoteAttr;
	protected String logContent;
	protected Throwable error;

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

	void clear() {
		this.version = null;
		this.methodName = null;
		this.remoteAttr = null;
		this.logContent = null;
		this.error = null;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getVersion() {
		return version;
	}

	public String getMethodName() {
		return methodName;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getTake() {
		return take;
	}

	public int getRespType() {
		return respType;
	}

	public String getRemoteAttr() {
		return remoteAttr;
	}

	public String getLogContent() {
		return logContent;
	}

	public Throwable getError() {
		return error;
	}
}
