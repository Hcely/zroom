package zr.monitor;

public interface ZRRequestHandler<T> {
	public Object executeNoMethod(T invoker) throws Throwable;

	public void onApiClose(T invoker, ZRRequest zreq) throws Throwable;

	public boolean onBefore(T invoker, ZRRequest zreq) throws Throwable;

	public void execute(T invoker, ZRRequest zreq) throws Throwable;

	public void onAfter(T invoker, ZRRequest zreq);

	public void onLog(T invoker, ZRRequest zreq, ZRLogContent logHr);

	public Throwable onError(T invoker, ZRRequest zreq, boolean resultError);
}
