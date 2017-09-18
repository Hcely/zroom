package zr.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;

import zr.mybatis.util.MapperConfigInfo;

final class Util {

	public static final Class<?> getType(Class<?> clazz) {
		LinkedList<Class<?>> stacks = new LinkedList<>();
		while (clazz.getSuperclass() != SimpleDao.class) {
			stacks.add(clazz);
			clazz = clazz.getSuperclass();
		}
		int idx = 0;
		while (clazz != null) {
			ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
			Type pt = type.getActualTypeArguments()[idx];
			if (pt instanceof Class)
				return (Class<?>) pt;
			TypeVariable<?>[] types = clazz.getTypeParameters();
			clazz = null;
			idx = 0;
			for (; idx < types.length; ++idx)
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

	public static final String build(MapperConfigInfo info) {
		StringBuilder sb = new StringBuilder(1024 * 10);
		
	}

}
