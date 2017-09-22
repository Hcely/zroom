package zr.common.filter;

public interface ZRFilter<E extends ZRMethodExecutor<T>, T> {
	public Object onFilter(E executor, T obj);
}
