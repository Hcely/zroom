package zr.common.interceptor;

public interface ZRInterceptor<E extends ZRMethodInvocation<T>, T> {
	public Object onFilter(E executor, T method);
}
