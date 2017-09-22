package zr.monitor;

public interface ZRRequestFilter<R extends ZRequest> {
	public boolean onBefore(R request) throws Throwable;

	public void onAfter(R request);

	public void onError(R request, boolean resultError);
}
