package zr.mybatis.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import v.server.helper.ClassHelper;
import zr.mybatis.annotation.IgnoreColumn;
import zr.mybatis.annotation.IncColumn;
import zr.mybatis.annotation.KeyColumn;

public class BeanInfo {

	static final BeanInfo create(Class<?> clazz) {
		Field[] fields = getFields(clazz);
		Field incColumn = null;
		List<Field> keys = new ArrayList<>(4);
		for (Field f : fields) {
			if (f.getAnnotation(IncColumn.class) != null)
				incColumn = f;
			if (f.getAnnotation(KeyColumn.class) != null)
				keys.add(f);
		}
		return new BeanInfo(clazz, fields, incColumn, keys.toArray(new Field[keys.size()]));
	}

	private static final Field[] getFields(Class<?> clazz) {
		Map<String, Field> map = new LinkedHashMap<>();
		getFields0(clazz, map);
		return map.values().toArray(new Field[map.size()]);
	}

	private static final void getFields0(Class<?> clazz, Map<String, Field> hr) {
		if (clazz.getSuperclass() != Object.class)
			getFields0(clazz, hr);
		for (Field f : clazz.getDeclaredFields()) {
			int mod = f.getModifiers();
			if (Modifier.isFinal(mod) || Modifier.isStatic(mod))
				continue;
			if (f.getAnnotation(Deprecated.class) != null)
				continue;
			if (f.getAnnotation(IgnoreColumn.class) != null)
				continue;
			f.setAccessible(true);
			hr.put(f.getName(), f);
		}
	}

	protected final Class<?> type;
	protected final Field[] fields;
	protected final Field incColumn;
	protected final Field[] keys;

	private BeanInfo(Class<?> type, Field[] fields, Field incColumn, Field[] keys) {
		this.type = type;
		this.fields = fields;
		this.incColumn = incColumn;
		this.keys = keys;
	}

	public Object newInstance() {
		return ClassHelper.newObj(type);
	}

	public Field[] getFields() {
		return fields;
	}

	public Class<?> getType() {
		return type;
	}

	public Field getIncColumn() {
		return incColumn;
	}

	public Field[] getKeys() {
		return keys;
	}

}
