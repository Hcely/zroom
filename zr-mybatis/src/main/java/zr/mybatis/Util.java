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
			if (pt instanceof ParameterizedType)
				return (Class<?>) ((ParameterizedType) pt).getRawType();
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
		Type pt = type.getActualTypeArguments()[0];
		if (pt instanceof ParameterizedType)
			return (Class<?>) ((ParameterizedType) pt).getRawType();
		return (Class<?>) pt;
	}

	public static final Object get(Field f, Object obj) {
		try {
			return f.get(obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static final void set(Field f, Object obj, Object value) {
		try {
			f.set(obj, value);
		} catch (Exception e) {
		}
	}

	public static final Map<String, Object> toMap(BeanInfo beanInfo, Object obj, boolean ignoreNull,
			boolean ignoreEmpty) {
		Map<String, Object> hr = new LinkedHashMap<>();
		for (Field f : beanInfo.getFields())
			try {
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

	public static final void setIncColumn(BeanInfo beanInfo, Object obj, Map<String, Object> map) {
		Field incField = beanInfo.getIncColumn();
		if (incField == null)
			return;
		Number number = (Number) map.get(incField.getName());
		number = getNumber(number, incField.getType());
		if (number == null)
			return;
		set(incField, obj, number);
	}

	public static final Number getNumber(final Number number, final Class<?> clazz) {
		if (number == null)
			return null;
		if (number.getClass() == clazz)
			return number;
		if (clazz == int.class || clazz == Integer.class)
			return Integer.valueOf(number.intValue());
		if (clazz == long.class || clazz == Long.class)
			return Long.valueOf(number.longValue());
		if (clazz == double.class || clazz == Double.class)
			return Double.valueOf(number.doubleValue());
		if (clazz == byte.class || clazz == Byte.class)
			return Byte.valueOf(number.byteValue());
		if (clazz == short.class || clazz == Short.class)
			return Short.valueOf(number.shortValue());
		if (clazz == float.class || clazz == Float.class)
			return Float.valueOf(number.floatValue());
		return null;
	}

}
