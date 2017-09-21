package zr.mybatis.operate;

import java.util.List;

import zr.mybatis.sql.SqlCriteria;

public interface SelectOperate<T> {
	public T find(T condition);

	public List<T> queryAll();

	public List<T> queryAll(SqlCriteria sorts);

	public List<T> query(T condition);

	public List<T> query(T condition, SqlCriteria sorts);
}
