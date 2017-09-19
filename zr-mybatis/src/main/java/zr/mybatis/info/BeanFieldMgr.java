package zr.mybatis.info;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import v.Clearable;

public class BeanFieldMgr implements Clearable {
	protected final Map<Class<?>, BeanInfo> fieldMap;

	public BeanFieldMgr() {
		fieldMap = new IdentityHashMap<>();
		init();
	}

	private void init() {
		fieldMap.put(Boolean.class, BeanInfo.BOOLEAN);
		fieldMap.put(Byte.class, BeanInfo.BYTE);
		fieldMap.put(Short.class, BeanInfo.SHORT);
		fieldMap.put(Integer.class, BeanInfo.INT);
		fieldMap.put(Long.class, BeanInfo.LONG);
		fieldMap.put(Float.class, BeanInfo.FLOAT);
		fieldMap.put(Double.class, BeanInfo.DOUBLE);
		fieldMap.put(String.class, BeanInfo.STR);
		fieldMap.put(Map.class, BeanInfo.MAP);
		fieldMap.put(HashMap.class, new BeanInfo(HashMap.class));
		fieldMap.put(LinkedHashMap.class, new BeanInfo(LinkedHashMap.class));
		fieldMap.put(TreeMap.class, new BeanInfo(TreeMap.class));
	}

	public BeanInfo getFields(Class<?> clazz) {
		BeanInfo info = fieldMap.get(clazz);
		if (info == null)
			synchronized (this) {
				if ((info = fieldMap.get(clazz)) == null) {
					if (Map.class.isAssignableFrom(clazz))
						info = new BeanInfo(clazz);
					else
						info = BeanInfo.create(clazz);
					fieldMap.put(clazz, info);
				}
			}
		return info;
	}

	@Override
	public void clear() {
		fieldMap.clear();
	}
}
