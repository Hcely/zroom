package zr.monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import v.common.util.AutoArray;
import zr.monitor.method.ZRMethod;
import zr.monitor.util.ZRKey;

@SuppressWarnings("unchecked")
public class ZRRequest {
	public static final byte RESULT_OK = 1;
	public static final byte RESULT_BAD = 2;
	public static final byte RESULT_ERROR = 3;

	protected final AutoArray<Object> flags;
	protected long startTime;
	protected long take;
	protected ZRMethod method;
	protected String removeIp;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Object hresult;
	protected byte resultStatus;
	protected Throwable error;

	public ZRRequest() {
		flags = new AutoArray<>(16, 16);
		reset();
	}

	@Override
	protected ZRRequest clone() {
		try {
			return (ZRRequest) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	ZRRequest set(ZRMethod method, String removeIp, HttpServletRequest request, HttpServletResponse response) {
		this.startTime = System.currentTimeMillis();
		this.method = method;
		this.removeIp = removeIp;
		this.request = request;
		this.response = response;
		return this;
	}

	ZRRequest reset() {
		this.take = 0;
		this.method = null;
		this.removeIp = null;
		this.request = null;
		this.response = null;
		this.resultStatus = 0;
		this.hresult = null;
		this.error = null;
		this.flags.clear();
		return this;
	}

	ZRRequest end(long endTime) {
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

	public String getRemoveIp() {
		return removeIp;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
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

	public <T> T getFlag(ZRKey key) {
		return (T) flags.opt(key.getId());
	}

	public void setFlag(ZRKey key, Object value) {
		this.flags.set(key.getId(), value);
	}

}
