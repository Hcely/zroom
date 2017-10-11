package zr.mybatis.sql;

public interface SqlCriteria {
	public static SqlCriteria create() {
		return new SqlCriteriaImpl();
	}

	public SqlCriteria addField(String key);

	public SqlCriteria addRawField(final String key);

	public SqlCriteria update(String key, Object value, boolean ignoreNull);

	public SqlCriteria update(String key, Object value);

	public SqlCriteria updateInc(String key, Number num);

	public SqlCriteria updateMin(String key, Number num);

	public SqlCriteria updateMax(String key, Number num);

	public SqlWhere where();

	public SqlCriteria groupBy(String... columns);

	public SqlHaving having();

	public SqlSorts sorts();

	public SqlCriteria limit(int count);

	public SqlCriteria limit(int offset, int count);

	public SqlCriteria setTailSql(String tailSql);

	public SqlCriteria resetFields();

	public SqlCriteria resetUpdates();

	public SqlCriteria resetWheres();

	public SqlCriteria resetGroupBy();

	public SqlCriteria resetHaving();

	public SqlCriteria resetSorts();

	public SqlCriteria resetLimit();

	public SqlCriteria resetTailSql();

	public SqlCriteria reset();

	public boolean isFieldValid();

	public boolean isUpdateValid();

	public boolean isWhereValid();

	public boolean isGroupByValid();

	public boolean isHavingValid();

	public boolean isSortValid();

	public boolean isLimitValid();

	public boolean isTailValid();

	public boolean containUpdate(String key);
}
