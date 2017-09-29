package zr.mybatis.operate;

import zr.mybatis.sql.ObjCriteria;

public interface DeleteOperate<T> {
	public int delete(T condition);

	public int delete(ObjCriteria<?> objCriteria);
}
