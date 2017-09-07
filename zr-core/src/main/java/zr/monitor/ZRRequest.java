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
	
	
}
