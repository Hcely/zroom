package zr.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;

public class ZRSpringUtil {

	public static final boolean isCglibProxy(Class<?> clazz) {
		return SpringProxy.class.isAssignableFrom(clazz) && clazz.getName().contains("$$");
	}

	public static final boolean isJdkProxy(Class<?> clazz) {
		return SpringProxy.class.isAssignableFrom(clazz) && Proxy.isProxyClass(clazz);
	}

	public static final boolean isCglibProxy(Object obj) {
		return isCglibProxy(obj.getClass());
	}

	public static final boolean isJdkProxy(Object obj) {
		return isJdkProxy(obj.getClass());
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

	public static final Object getRawObj(Object obj) {
		try {
			if (isCglibProxy(obj))
				return getRawCGLibProxy(obj);
			else if (isJdkProxy(obj))
				return getRawJdkProxy(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private static final Object getRawCGLibProxy(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		Field f = clazz.getDeclaredField("CGLIB$CALLBACK_0");
		f.setAccessible(true);
		Object dynamicAdvisedInterceptor = f.get(obj);
		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
		return target;
	}

	private static final Object getRawJdkProxy(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		Field h = clazz.getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(obj);
		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
		return target;
	}
}
