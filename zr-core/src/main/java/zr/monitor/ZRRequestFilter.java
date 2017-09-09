package zr.monitor;

public interface ZRRequestFilter {
	public boolean onBefore(ZRRequest request) throws Throwable;

	public void onAfter(ZRRequest request);

	public void onError(ZRRequest request, boolean resultError);
}
