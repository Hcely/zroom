package zr.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

final class Util {

	public static final Object getRawObj(Object obj) {
		try {
			if (AopUtils.isCglibProxy(obj))
				return getRawCGLibProxy(obj);
			else if (AopUtils.isJdkDynamicProxy(obj))
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

	public static final Class<?> getType(Class<?> clazz) {
		LinkedList<Class<?>> stacks = new LinkedList<>();
		while (clazz.getSuperclass() != SimpleDao.class) {
			stacks.add(clazz);
			clazz = clazz.getSuperclass();
		}
		int idx = 0, len;
		while (clazz != null) {
			Type type = clazz.getGenericSuperclass();
			if (!(type instanceof ParameterizedType))
				break;
			Type pt = ((ParameterizedType) type).getActualTypeArguments()[idx];
			if (pt instanceof Class)
				return (Class<?>) pt;
			TypeVariable<?>[] types = clazz.getTypeParameters();
			clazz = null;
			for (idx = 0, len = types.length; idx < len; ++idx)
				if (pt == types[idx]) {
					clazz = stacks.pollLast();
					break;
				}
		}
		throw new RuntimeException("can not get type of SimpleDao");
	}

	public static final Class<?> getType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<?>) type.getActualTypeArguments()[0];
	}

}
