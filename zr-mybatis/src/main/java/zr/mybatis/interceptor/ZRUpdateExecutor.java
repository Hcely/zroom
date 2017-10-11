package zr.mybatis.interceptor;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;

public interface ZRUpdateExecutor {
	public int update(BeanInfo info, SqlCriteria criteria);
}
