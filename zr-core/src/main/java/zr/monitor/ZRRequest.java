package zr.monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zr.monitor.method.ZRMethod;

@SuppressWarnings("unchecked")
public class ZRRequest {
	private static final ZRRequest INSTANCE = new ZRRequest();

	public static final ZRRequest create() {
		return INSTANCE.clone();
	}

	public static final byte RESULT_OK = 1;
	public static final byte RESULT_BAD = 2;
	public static final byte RESULT_ERROR = 3;

	protected long startTime;
	protected long take;
	protected ZRMethod method;
	protected String removeIp;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Object hresult;
	protected byte resultType;
	protected Throwable error;
	protected Object flag0;
	protected Object flag1;
	protected Object flag2;

	private ZRRequest() {
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
		this.resultType = 0;
		this.hresult = null;
		this.error = null;
		this.flag0 = null;
		this.flag1 = null;
		this.flag2 = null;
		return this;
	}

	ZRRequest end() {
		take = System.currentTimeMillis() - startTime;
		return this;
	}

	public void setHResult(Object hresult, byte resultType) {
		this.hresult = hresult;
		this.resultType = resultType;
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

	public byte getResultType() {
		return resultType;
	}

	public Object getHresult() {
		return hresult;
	}

	public Throwable getError() {
		return error;
	}

	public <T> T getFlag0() {
		return (T) flag0;
	}

	public void setFlag0(Object flag0) {
		this.flag0 = flag0;
	}

	public <T> T getFlag1() {
		return (T) flag1;
	}

	public void setFlag1(Object flag1) {
		this.flag1 = flag1;
	}

	public <T> T getFlag2() {
		return (T) flag2;
	}

	public void setFlag2(Object flag2) {
		this.flag2 = flag2;
	}

}
