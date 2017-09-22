package zr.monitor;

import zr.common.util.ZRKey;
import zr.common.util.ZRKeyMap;
import zr.monitor.method.ZRMethod;

public abstract class ZRequest {
	public static final byte RESULT_OK = 1;
	public static final byte RESULT_BAD = 2;
	public static final byte RESULT_ERROR = 3;

	private final ZRKeyMap flagMap;

	protected long startTime;
	protected ZRMethod method;

	private Object hresult;
	private byte resultStatus;
	private long take;
	private Throwable error;

	public ZRequest() {
		flagMap = new ZRKeyMap(16, 16);
	}

	protected void set(long startTime, ZRMethod method) {
		this.startTime = startTime;
		this.method = method;
	}

	protected void reset() {
		this.method = null;
		this.hresult = null;
		this.error = null;
		this.flagMap.clear();
	}

	ZRequest end(long endTime) {
		take = endTime - startTime;
		return this;
	}

	void setError(Throwable error) {
		this.error = error;
		this.resultStatus = RESULT_ERROR;
	}

	public void setHResult(Object hresult, byte resultStatus) {
		this.hresult = hresult;
		this.resultStatus = resultStatus;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getTake() {
		return take;
	}

	public ZRMethod getMethod() {
		return method;
	}

	public byte getResultStatus() {
		return resultStatus;
	}

	public Object getHresult() {
		return hresult;
	}

	public Throwable getError() {
		return error;
	}

	public <V> V getFlag(ZRKey<V> key) {
		return (V) flagMap.get(key);
	}

	public <V> void setFlag(ZRKey<V> key, V value) {
		flagMap.set(key, value);
	}

	public <V> V removeFlag(ZRKey<V> key) {
		return flagMap.remove(key);
	}

	public abstract String getRemoteIp();

	public abstract String getReqId();

	public abstract String getReqPrevId();

	public abstract String getReqSilkId();

	public abstract boolean isReqHeaderNode();

}
