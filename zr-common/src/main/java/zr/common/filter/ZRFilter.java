package zr.common.filter;

public interface ZRFilter<E extends ZRMethodInvocation<T>, T> {
	public Object onFilter(E executor, T method);
}
