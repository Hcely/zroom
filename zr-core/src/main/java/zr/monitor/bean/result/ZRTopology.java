package zr.monitor.bean.result;

import zr.monitor.util.ZRMonitorUtil;

public class ZRTopology {
	protected String prevId;
	protected String silkId;
	protected String methodName;
	protected String version;
	protected long startTime;
	protected long take;
	protected byte resultStatus;
	protected int num;

	public ZRTopology() {
	}

	public ZRTopology(String prevId, String silkId, String methodName, String version, long startTime) {
		this.prevId = prevId;
		this.silkId = silkId;
		this.methodName = methodName;
		this.version = version;
		this.startTime = startTime;
	}

	public ZRTopology finish(final long endTime, final byte resultStatus) {
		this.take = endTime - startTime;
		this.resultStatus = resultStatus;
		return this;
	}

	public String nextSilkId() {
		return ZRMonitorUtil.buildSilkId(silkId.hashCode(), num++);
	}

	public String getPrevId() {
		return prevId;
	}

	public void setPrevId(String prevId) {
		this.prevId = prevId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSilkId() {
		return silkId;
	}

	public void setSilkId(String silkId) {
		this.silkId = silkId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getTake() {
		return take;
	}

	public void setTake(long take) {
		this.take = take;
	}

	public byte getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(byte resultStatus) {
		this.resultStatus = resultStatus;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
