package zr.monitor.bean.result;

public class ZRTopologyResult {
	protected String version;
	protected String methodName;
	protected long startTime;
	protected long take;
	protected byte resultType;
	protected String reqId;
	protected String prevId;
	protected String silkId;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	public byte getResultType() {
		return resultType;
	}

	public void setResultType(byte resultType) {
		this.resultType = resultType;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getPrevId() {
		return prevId;
	}

	public void setPrevId(String prevId) {
		this.prevId = prevId;
	}

	public String getSilkId() {
		return silkId;
	}

	public void setSilkId(String silkId) {
		this.silkId = silkId;
	}

}
