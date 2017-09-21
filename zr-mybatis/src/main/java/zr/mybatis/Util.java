package zr.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import zr.mybatis.annotation.MapperConfig;
import zr.mybatis.info.BeanInfo;

final class Util {
	private static int incNum = 0;

	public static final String getNextNamespace() {
		return new StringBuilder(16).append("_zrMapper_").append(incNum++).toString();
	}

	public static final List<MapperField> getMapperFields(Class<?> clazz) {
		LinkedHashMap<String, MapperField> hr = new LinkedHashMap<>();
		getMapperFields(clazz, hr);
		return new ArrayList<>(hr.values());
	}

	public static final MapperConfig getDaoConfig(Class<?> clazz) {
		while (clazz != Object.class) {
			MapperConfig config = clazz.getAnnotation(MapperConfig.class);
			if (config != null)
				return config;
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	private static final void getMapperFields(Class<?> clazz, Map<String, MapperField> hr) {
		if (clazz.getSuperclass() != Object.class)
			getMapperFields(clazz.getSuperclass(), hr);
		for (Field f : clazz.getDeclaredFields()) {
			int mod = f.getModifiers();
			if (Modifier.isFinal(mod))
				continue;
			if (f.getType() != SimpleMapper.class)
				continue;
			MapperConfig config = f.getAnnotation(MapperConfig.class);
			if (config == null)
				continue;
			f.setAccessible(true);
			hr.put(f.getName(), new MapperField(f, config));
		}
	}

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

	public static final Class<?> getDaoGenericType(Class<?> clazz) {
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

	public static final Class<?> getFieldGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<?>) type.getActualTypeArguments()[0];
	}

	public static final Object get(Field f, Object obj) {
		try {
			return f.get(obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static final Map<String, Object> toMap(BeanInfo beanInfo, Object obj, boolean ignoreNull,
			boolean ignoreEmpty) {
		Map<String, Object> hr = new LinkedHashMap<>();
		Field incField = beanInfo.getIncColumn();
		for (Field f : beanInfo.getFields())
			try {
				if (f == incField)
					continue;
				Object value = f.get(obj);
				if (ignoreNull && value == null)
					continue;
				if (ignoreEmpty && value instanceof CharSequence)
					if (((CharSequence) value).length() == 0)
						continue;
				hr.put(f.getName(), value);
			} catch (Exception e) {
			}
		return hr;
	}

}
