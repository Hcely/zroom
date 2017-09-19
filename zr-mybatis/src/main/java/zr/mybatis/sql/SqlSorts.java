package zr.mybatis.sql;

public interface SqlSorts {
	public SqlSorts clear();

	public SqlCriteria end();

	public SqlSorts asc(String key);

	public SqlSorts desc(String key);

}
