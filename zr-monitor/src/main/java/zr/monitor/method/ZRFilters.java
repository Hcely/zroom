package zr.monitor.method;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import v.Clearable;
import zr.monitor.ZRRequestFilter;
import zr.monitor.annotation.ZRFilter;

@SuppressWarnings("rawtypes")
class ZRFilters implements Clearable {
	static final ZRRequestFilter[] EMPTY = {};

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

	public ZRRequestFilter[] getFilters(Method method) {
		ZRFilter anno = method.getDeclaringClass().getAnnotation(ZRFilter.class);
		List<Class<?>> list = new LinkedList<>();
		if (anno != null)
			for (Class<?> e : anno.value())
				if (!list.contains(e))
					list.add(e);
		anno = method.getAnnotation(ZRFilter.class);
		if (anno != null)
			for (Class<?> e : anno.value())
				if (!list.contains(e))
					list.add(e);
		if (list.isEmpty())
			return EMPTY;
		ZRRequestFilter[] filters = new ZRRequestFilter[list.size()];
		int i = 0;
		for (Class<?> e : list)
			filters[i++] = getFilter(e);
		return filters;

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
