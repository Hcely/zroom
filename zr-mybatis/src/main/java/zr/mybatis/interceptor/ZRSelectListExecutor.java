package zr.mybatis.interceptor;

import java.util.List;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.sql.SqlCriteria;
@SuppressWarnings("rawtypes")
public interface ZRSelectListExecutor {
	public List selectList(BeanInfo info, SqlCriteria criteria, boolean map);
}
