package zr.monitor;

public interface ZRRequestFilter {
	public boolean onBefore(ZRRequest request);

	public void onAfter(ZRRequest request, Object response);

	public void onError(ZRRequest request, Throwable ex, boolean methodError);
}
