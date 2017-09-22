package zr.monitor;

public interface ZRRequestHandler<T, R extends ZRequest> {
	public Object executeNoMethod(T invoker, R zreq) throws Throwable;

	public void onApiClose(T invoker, R zreq) throws Throwable;

	public boolean onBefore(T invoker, R zreq) throws Throwable;

	public void execute(T invoker, R zreq) throws Throwable;

	public void onAfter(T invoker, R zreq);

	public void onLog(T invoker, R zreq, ZRLogContent logHr);

	public Throwable onError(T invoker, R zreq, boolean resultError);
}
