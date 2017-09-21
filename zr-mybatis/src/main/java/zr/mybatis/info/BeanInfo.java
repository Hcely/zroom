package zr.mybatis.info;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import zr.mybatis.annotation.Ignore;
import zr.mybatis.annotation.IncColumn;
import zr.mybatis.annotation.KeyColumn;

public class BeanInfo {
	private static final Field[] EMPTY_FIELDS = {};

	public static final BeanInfo BOOLEAN = new BeanInfo(Boolean.class, "boolean");
	public static final BeanInfo BYTE = new BeanInfo(Byte.class, "byte");
	public static final BeanInfo SHORT = new BeanInfo(Short.class, "short");
	public static final BeanInfo INT = new BeanInfo(Integer.class, "int");
	public static final BeanInfo LONG = new BeanInfo(Long.class, "long");
	public static final BeanInfo FLOAT = new BeanInfo(Float.class, "float");
	public static final BeanInfo DOUBLE = new BeanInfo(Double.class, "double");
	public static final BeanInfo MAP = new BeanInfo(Map.class, "map");
	public static final BeanInfo STR = new BeanInfo(String.class, "string");

	static final BeanInfo create(Class<?> clazz) {
		Field[] fields = getFields(clazz);
		Field incColumn = null;
		List<Field> keys = new ArrayList<>(4);
		List<Field> normals = new ArrayList<>(fields.length);
		for (Field f : fields) {
			if (f.getAnnotation(IncColumn.class) != null)
				incColumn = f;
			KeyColumn anno0 = f.getAnnotation(KeyColumn.class);
			if (anno0 != null)
				keys.add(f);
			else
				normals.add(f);
		}
		Field[] keys0 = keys.toArray(new Field[keys.size()]);
		Field[] normal0 = normals.toArray(new Field[normals.size()]);
		return new BeanInfo(clazz, fields, incColumn, keys0, normal0);
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
			if (f.getAnnotation(Ignore.class) != null)
				continue;
			f.setAccessible(true);
			hr.put(f.getName(), f);
		}
	}

	protected final Class<?> type;
	protected final String typeName;
	protected final Field[] fields;
	protected final Field incColumn;
	protected final Field[] keys;
	protected final Field[] normals;
	protected final boolean baseBean;
	protected final boolean map;

	BeanInfo(Class<?> type) {
		this(type, type.getName());
	}

	BeanInfo(Class<?> type, String typeName) {
		this.type = type;
		this.typeName = typeName;
		this.fields = EMPTY_FIELDS;
		this.incColumn = null;
		this.keys = EMPTY_FIELDS;
		this.normals = EMPTY_FIELDS;
		this.baseBean = true;
		this.map = Map.class.isAssignableFrom(type);
	}

	private BeanInfo(Class<?> type, Field[] fields, Field incColumn, Field[] keys, Field[] normals) {
		this.type = type;
		this.typeName = type.getName();
		this.fields = fields;
		this.incColumn = incColumn;
		this.keys = keys;
		this.normals = normals;
		this.baseBean = false;
		this.map = false;
	}

	public Class<?> getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}

	public Field[] getFields() {
		return fields;
	}

	public Field getIncColumn() {
		return incColumn;
	}

	public Field[] getKeys() {
		return keys;
	}

	public Field[] getNormals() {
		return normals;
	}

	public boolean isBaseBean() {
		return baseBean;
	}

	public boolean isMap() {
		return map;
	}

}
