package zr.mybatis.util;

import java.util.IdentityHashMap;
import java.util.Map;

import v.Clearable;

public class BeanFieldMgr implements Clearable {
	protected final Map<Class<?>, BeanInfo> fieldMap;

	public BeanFieldMgr() {
		fieldMap = new IdentityHashMap<>();
	}

	public BeanInfo getFields(Class<?> clazz) {
		BeanInfo info = fieldMap.get(clazz);
		if (info == null)
			synchronized (this) {
				if ((info = fieldMap.get(clazz)) == null)
					fieldMap.put(clazz, info = BeanInfo.create(clazz));
			}
		return info;
	}

	@Override
	public void clear() {
		fieldMap.clear();
	}
}
