package zr.monitor;

public interface ZRMonitorHandler {

	public void onApiClose();

	public void onRequestStart();

	public void onRequestEnd();

	public Throwable handleError();

}
