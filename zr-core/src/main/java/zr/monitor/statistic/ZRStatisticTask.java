package zr.monitor.statistic;

import v.ObjBuilder;
import zr.monitor.method.ZRMethod;

public class ZRStatisticTask implements Cloneable {
	private static final ZRStatisticTask INSTANCE = new ZRStatisticTask();
	public static final ObjBuilder<ZRStatisticTask> BUILDER = new ObjBuilder<ZRStatisticTask>() {

		@Override
		public Class<ZRStatisticTask> getType() {
			return ZRStatisticTask.class;
		}

		@Override
		public ZRStatisticTask build() {
			return INSTANCE.clone();
		}
	};
	protected String serviceId;
	protected String version;
	protected String methodName;
	protected long startTime;
	protected long take;
	protected int respType;
	protected String remoteAttr;
	protected String logContent;
	protected Object msg;
	protected Throwable error;

	public ZRStatisticTask() {

	}

	@Override
	protected ZRStatisticTask clone() {
		try {
			return (ZRStatisticTask) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	void set(String serviceId, ZRMethod method, long startTime, long take, int respType, String remoteAttr,
			String logContent, Object msg, Throwable error) {
		this.serviceId = serviceId;
		this.version = method.getVersion();
		this.methodName = method.getMethodName();
		this.startTime = startTime;
		this.take = take;
		this.respType = respType;
		this.remoteAttr = remoteAttr;
		this.logContent = logContent;
		this.msg = msg;
		this.error = error;
	}

	void clear() {
		this.serviceId = null;
		this.version = null;
		this.methodName = null;
		this.remoteAttr = null;
		this.logContent = null;
		this.msg = null;
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

	public Object getMsg() {
		return msg;
	}

	public Throwable getError() {
		return error;
	}
}
