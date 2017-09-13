package zr.monitor.method;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import v.Clearable;
import zr.monitor.ZRRequestFilter;

class ZRFilters implements Clearable {
	protected final Map<Class<?>, ZRRequestFilter> filterMap;

	ZRFilters() {
		filterMap = new IdentityHashMap<>();
	}

	public void addFilters(Collection<ZRRequestFilter> filters) {
		if (filters != null)
			for (ZRRequestFilter e : filters)
				filterMap.put(e.getClass(), e);
	}

	public void addFilter(ZRRequestFilter filter) {
		filterMap.put(filter.getClass(), filter);
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

	@Override
	public void clear() {
		filterMap.clear();
	}
}
