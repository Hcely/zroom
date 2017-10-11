package zr.mybatis.interceptor;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;

public interface ZRDeleteExecutor {

	public int delete(BeanInfo info, SqlCriteria criteria);
}
