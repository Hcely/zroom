package zr.mybatis;

import java.util.Map;

import zr.mybatis.sql.SqlCriteria;

public interface ZRMybatisFilter {
	public int getPriority();

	public void onInsertObj(String table, Object obj);

	public void onInsertMap(String table, Map<String, Object> map);

	public void onSelect(String table, SqlCriteria criteria, boolean selectOne);

	public void onUpdate(String table, SqlCriteria criteria);

	public void onDelete(String table, SqlCriteria criteria);

}
