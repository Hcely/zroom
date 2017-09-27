package zr.common.interceptor;

import zr.common.util.ZRKey;

public interface ZRMethodInvocation<T> {
	public Object execute(T method) throws Throwable;

	public <V> void setFlag(ZRKey<V> key, V value);

	public <V> V getFlag(ZRKey<V> key);

	public <V> V removeFlag(ZRKey<V> key);
}
