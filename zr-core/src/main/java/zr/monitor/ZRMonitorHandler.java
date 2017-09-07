package zr.monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ZRMonitorHandler {
	public HttpServletRequest getRequest();

	public HttpServletResponse getResponse();

	public String getIp(HttpServletRequest request);

	public void onApiClose();

	public void onRequestStart();

	public void onRequestEnd();

	public Throwable handleError();

}
