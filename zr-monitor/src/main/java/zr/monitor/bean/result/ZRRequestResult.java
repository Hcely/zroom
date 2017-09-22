package zr.monitor.bean.result;

import java.util.Map;

public class ZRRequestResult {
	protected String version;
	protected String methodName;
	protected String remoteIp;
	protected long startTime;
	protected long take;
	protected byte resultStatus;
	protected String reqId;
	protected String logContent;
	protected Map<String, String> flags;
	protected Throwable error;

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

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
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

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Map<String, String> getFlags() {
		return flags;
	}

	public void setFlags(Map<String, String> flags) {
		this.flags = flags;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

}
