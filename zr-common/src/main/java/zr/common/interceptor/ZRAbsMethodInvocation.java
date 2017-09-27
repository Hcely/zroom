package zr.common.interceptor;

import zr.common.util.ZRKey;
import zr.common.util.ZRKeyMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class ZRAbsMethodInvocation<T> implements ZRMethodInvocation<T> {
	protected ZRInterceptor[] filters;
	protected int length;
	protected int stackIdx;
	protected final ZRKeyMap map;

	public ZRAbsMethodInvocation() {
		this.filters = null;
		this.length = 0;
		this.stackIdx = 0;
		this.map = new ZRKeyMap();
	}

	public Object execute(final ZRInterceptor[] filters, final T method) throws Throwable {
		this.filters = filters;
		this.length = filters.length;
		this.stackIdx = 0;
		map.clear();
		return execute(method);
	}

	@Override
	public Object execute(final T method) throws Throwable {
		if (stackIdx < length)
			return filters[stackIdx++].onFilter(this, method);
		else
			return execute0(method);
	}

	protected abstract Object execute0(T method) throws Throwable;

	@Override
	public <V> void setFlag(final ZRKey<V> key, final V value) {
		map.set(key, value);
	}

	@Override
	public <V> V getFlag(final ZRKey<V> key) {
		return (V) map.get(key);
	}

	@Override
	public <V> V removeFlag(final ZRKey<V> key) {
		return (V) map.remove(key);
	}

}
