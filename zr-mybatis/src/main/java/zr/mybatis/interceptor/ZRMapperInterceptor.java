package zr.mybatis.interceptor;

import java.util.List;
import java.util.Map;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;

@SuppressWarnings("rawtypes")
public interface ZRMapperInterceptor {
	public default int getPriority() {
		return Integer.MAX_VALUE;
	}

	public default int onInsertObj(ZRInsertObjExecutor executor, BeanInfo info, Object obj) {
		return executor.insertObj(info, obj);
	}

	public default int onInsertMap(ZRInsertMapExecutor executor, BeanInfo info, Map<String, Object> map) {
		return executor.insertMap(info, map);
	}

	public default Object onSelectOne(ZRSelectOneExecutor executor, BeanInfo info, SqlCriteria criteria, boolean map) {
		return executor.selectOne(info, criteria, map);
	}

	public default List onSelectList(ZRSelectListExecutor executor, BeanInfo info, SqlCriteria criteria, boolean map) {
		return executor.selectList(info, criteria, map);
	}

	public default int onUpdate(ZRUpdateExecutor executor, BeanInfo info, SqlCriteria criteria) {
		return executor.update(info, criteria);
	}

	public default int onDelete(ZRDeleteExecutor executor, BeanInfo info, SqlCriteria criteria) {
		return executor.delete(info, criteria);
	}
}
