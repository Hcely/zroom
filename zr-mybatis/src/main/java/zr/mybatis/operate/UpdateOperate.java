package zr.mybatis.operate;

import zr.mybatis.sql.ObjCriteria;

public interface UpdateOperate<T> {
	public int update(T update);

	public int update(T update, T condition);

	public int update(ObjCriteria<?> objCriteria);

	public int update(T update, ObjCriteria<?> condition);
}
