package zr.spring.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import v.common.helper.StrUtil;

public class ZRSpringUtil {
	protected static final List<String> DEF_METHODS = Arrays.asList("GET", "POST");

	public static final List<Method> getRequestMethod(Class<?> clazz) {
		List<Method> hr = new LinkedList<>();
		getRequestMethod(clazz, hr);
		return hr;
	}

	private static final void getRequestMethod(Class<?> clazz, List<Method> hr) {
		if (clazz.getSuperclass() != Object.class)
			getRequestMethod(clazz.getSuperclass(), hr);
		for (Method e : clazz.getDeclaredMethods()) {
			if (Modifier.isStatic(e.getModifiers()))
				continue;
			if (e.getAnnotation(RequestMapping.class) == null)
				continue;
			hr.add(e);
		}
	}

	public static final List<String> getUris(Method method) {
		Set<String> parentUris = getClassUri(method.getDeclaringClass());
		RequestMapping anno = method.getAnnotation(RequestMapping.class);
		Set<String> uris = new HashSet<>();
		for (String p : parentUris) {
			for (String e : anno.value())
				uris.add(getUri(p, e));
			for (String e : anno.path())
				uris.add(getUri(p, e));
		}
		List<String> hr = new ArrayList<>(uris);
		Collections.sort(hr);
		return hr;
	}

	private static final Set<String> getClassUri(Class<?> clazz) {
		Set<String> hr = new HashSet<>();
		RequestMapping anno = clazz.getAnnotation(RequestMapping.class);
		if (anno != null) {
			for (String e : anno.value())
				hr.add(checkPath(e));
			for (String e : anno.path())
				hr.add(checkPath(e));
		}
		if (hr.isEmpty())
			hr.add("");
		return hr;
	}

	public static final List<String> getMethods(Method method) {
		RequestMapping anno = method.getAnnotation(RequestMapping.class);
		RequestMethod[] methods = anno.method();
		if (methods.length == 0)
			return DEF_METHODS;
		Set<RequestMethod> set = new HashSet<>();
		for (RequestMethod e : methods)
			set.add(e);
		List<String> hr = new ArrayList<>(set.size());
		for (RequestMethod e : set)
			hr.add(e.toString());
		Collections.sort(hr);
		return hr;
	}

	private static final String getUri(String parent, String key) {
		int len = key.length();
		if (len == 0)
			return parent;
		boolean b0 = key.charAt(0) != '/';
		boolean b1 = key.charAt(len - 1) == '/';
		if (b0)
			++len;
		if (b1)
			--len;
		len += parent.length();
		StringBuilder sb = new StringBuilder(len);
		sb.append(parent);
		if (b0)
			sb.append('/');
		if (b1)
			StrUtil.appendValue(sb, key, 0, key.length() - 1);
		else
			sb.append(key);
		return StrUtil.sbToString(sb);
	}

	private static final String checkPath(String path) {
		int len = path.length();
		if (len == 0)
			return path;
		boolean b0 = path.charAt(0) == '/';
		boolean b1 = path.charAt(len - 1) != '/';
		if (b0 && b1)
			return path;
		if (!b0)
			++len;
		if (!b1)
			--len;
		StringBuilder sb = new StringBuilder(len);
		if (!b0)
			sb.append('/');
		if (b1)
			sb.append(path);
		else
			StrUtil.appendValue(sb, path, 0, path.length() - 1);
		return StrUtil.sbToString(sb);
	}

	public static final boolean isCglibProxy(Class<?> clazz) {
		return SpringProxy.class.isAssignableFrom(clazz) && clazz.getName().contains("$$");
	}

	public static final boolean isJdkProxy(Class<?> clazz) {
		return SpringProxy.class.isAssignableFrom(clazz) && Proxy.isProxyClass(clazz);
	}

	public static final Class<?> getRawClass(final Class<?> clazz) {
		Class<?> clz = clazz;
		while (isCglibProxy(clz))
			clz = clazz.getSuperclass();
		return clz;
	}

	public static final Method getRawMethod(final Method method) {
		Class<?> clazz = method.getDeclaringClass();
		Class<?> rawClazz = getRawClass(clazz);
		if (rawClazz == clazz)
			return method;
		try {
			return rawClazz.getMethod(method.getName(), method.getParameterTypes());
		} catch (Exception e) {
			return method;
		}
	}

	public static final Object getRawCGLibProxyObj(Object obj) {
		Class<?> clazz = obj.getClass();
		try {
			if (isJdkProxy(clazz))
				return getRawJdkProxy(clazz, obj);
			else if (isCglibProxy(clazz))
				return getRawCGLibProxy(clazz, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private static final Object getRawCGLibProxy(Class<?> clazz, Object obj) throws Exception {
		Field f = clazz.getDeclaredField("CGLIB$CALLBACK_0");
		f.setAccessible(true);
		Object dynamicAdvisedInterceptor = f.get(obj);
		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
		return target;
	}

	private static final Object getRawJdkProxy(Class<?> clazz, Object proxy) throws Exception {
		Field h = clazz.getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);
		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
		return target;
	}
}
