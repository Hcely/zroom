package zr.monitor;

import javax.servlet.http.HttpServletRequest;

import zr.monitor.method.ZRMethod;

public class ZRRequest {
	protected long startTime;
	protected ZRMethod method;
	protected String ip;
	protected HttpServletRequest request;
	protected Object response;
	protected Throwable error;

	ZRRequest() {
	}

	ZRRequest set(ZRMethod method, String ip, HttpServletRequest request) {
		this.startTime = System.currentTimeMillis();
		this.method = method;
		this.ip = ip;
		this.request = request;
		this.response = null;
		this.error = null;
		return this;
	}

	ZRRequest reset() {
		this.method = null;
		this.ip = null;
		this.request = null;
		this.response = null;
		this.error = null;
		return this;
	}

}
