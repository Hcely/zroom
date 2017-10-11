package zr.mybatis.interceptor;

import java.util.List;
import java.util.Map;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;

@SuppressWarnings("rawtypes")
public interface ZRMapperInterceptor {
	public int getPriority();

	public int onInsertObj(ZRInsertObjExecutor executor, BeanInfo info, Object obj);

	public int onInsertMap(ZRInsertObjExecutor executor, BeanInfo info, Map<String, Object> map);

	public Object onSelectOne(ZRSelectOneExecutor executor, BeanInfo info, SqlCriteria criteria, boolean map);

	public List onSelectList(ZRSelectListExecutor executor, BeanInfo info, SqlCriteria criteria, boolean map);

	public int onUpdate(ZRUpdateExecutor executor, BeanInfo info, SqlCriteria criteria);

	public int onDelete(ZRDeleteExecutor executor, BeanInfo info, SqlCriteria criteria);
}
