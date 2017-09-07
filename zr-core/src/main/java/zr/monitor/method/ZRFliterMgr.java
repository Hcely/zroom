package zr.monitor.method;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import zr.monitor.ZRRequestFilter;

public class ZRFliterMgr {
	protected final Map<Class<?>, ZRRequestFilter> filterMap;

	ZRFliterMgr(Set<ZRRequestFilter> filters) {
		filterMap = new IdentityHashMap<>();
		for (ZRRequestFilter e : filters)
			filterMap.put(e.getClass(), e);
	}

	public ZRRequestFilter getFilter(Class<?> clazz) {
		ZRRequestFilter filter = filterMap.get(clazz);
		if (filter != null)
			return filter;
		for (Entry<Class<?>, ZRRequestFilter> e : filterMap.entrySet())
			if (clazz.isAssignableFrom(e.getKey()))
				return e.getValue();
		throw new RuntimeException();
	}
}
