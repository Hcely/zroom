package zr.mybatis.sql;

public interface SqlSorts {
	public SqlSorts clear();

	public SqlCriteria end();

	public SqlSorts asc(String name);

	public SqlSorts desc(String name);

	public SqlSorts sort(String name, boolean asc);
}
