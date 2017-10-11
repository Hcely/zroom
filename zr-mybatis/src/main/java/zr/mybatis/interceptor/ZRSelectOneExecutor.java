package zr.mybatis.interceptor;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;

public interface ZRSelectOneExecutor {
	public Object selectOne(BeanInfo info, SqlCriteria criteria, boolean map);
}
