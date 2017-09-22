package zr.monitor.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import v.common.helper.StrUtil;
import zr.monitor.annotation.ZRAuthority;
import zr.monitor.annotation.ZRFilter;
import zr.monitor.annotation.ZRModule;
import zr.monitor.annotation.ZRVersion;

public class ZRMethodUtil {

	public static final String getMethodModule(Method method) {
		ZRModule anno = method.getAnnotation(ZRModule.class);
		if (anno != null) {
			String hr = anno.value();
			if (!StrUtil.isEmpty(hr))
				return hr;
		}
		Class<?> clazz = method.getDeclaringClass();
		while (clazz != Object.class) {
			if ((anno = clazz.getAnnotation(ZRModule.class)) != null) {
				String hr = anno.value();
				if (!StrUtil.isEmpty(hr))
					return hr;
			}
			clazz = clazz.getSuperclass();
		}
		return method.getDeclaringClass().getName();
	}

	public static final String getMethodName(Method method) {
		String clazzName = method.getDeclaringClass().getName();
		String methodName = method.getName();
		StringBuilder sb = new StringBuilder(clazzName.length() + methodName.length() + 3);
		sb.append(clazzName).append('.').append(methodName).append("()");
		return StrUtil.sbToString(sb);
	}

	public static final String getMethodVersion(Method method) {
		ZRVersion version = method.getAnnotation(ZRVersion.class);
		return version == null ? "0.0.0" : version.value();
	}

	public static final String getMethodFullName(String methodName, String version) {
		StringBuilder sb = new StringBuilder(methodName.length() + version.length() + 1);
		sb.append(methodName).append('-').append(version);
		return StrUtil.sbToString(sb);
	}

	public static final List<String> getAuthoritys(Method method) {
		LinkedHashMap<String, Void> hr = new LinkedHashMap<>();
		getAuthoritys(method.getDeclaringClass(), hr);
		getAuthoritys(method.getAnnotation(ZRAuthority.class), hr);
		return new ArrayList<>(hr.keySet());
	}

	private static final void getAuthoritys(Class<?> clazz, LinkedHashMap<String, Void> hr) {
		if (clazz.getSuperclass() != Object.class)
			getAuthoritys(clazz.getSuperclass(), hr);
		getAuthoritys(clazz.getAnnotation(ZRAuthority.class), hr);
	}

	private static final void getAuthoritys(ZRAuthority anno, LinkedHashMap<String, Void> hr) {
		if (anno == null)
			return;
		for (String e : anno.values())
			hr.put(e, null);
	}

	public static final List<Class<?>> getFilters(Method method) {
		LinkedHashMap<Class<?>, Void> hr = new LinkedHashMap<>();
		getFilters(method.getDeclaringClass(), hr);
		getFilters(method.getAnnotation(ZRFilter.class), hr);
		return new ArrayList<>(hr.keySet());
	}

	private static final void getFilters(Class<?> clazz, LinkedHashMap<Class<?>, Void> hr) {
		if (clazz.getSuperclass() != Object.class)
			getFilters(clazz.getSuperclass(), hr);
		getFilters(clazz.getAnnotation(ZRFilter.class), hr);
	}

	private static final void getFilters(ZRFilter anno, LinkedHashMap<Class<?>, Void> hr) {
		if (anno == null)
			return;
		for (Class<?> c : anno.value())
			hr.put(c, null);
	}
}
