package zr.mybatis.sql;

import java.util.Collection;

public interface SqlHaving {
	public SqlHaving clear();

	public SqlCriteria end();

	public SqlCriteria eq(String key, Object value, boolean ignoreNull);

	public SqlCriteria eq(String key, Object value);

	public SqlCriteria eqNull(String key, Object value);

	public SqlCriteria notEq(String key, Object value, boolean ignoreNull);

	public SqlCriteria notEq(String key, Object value);

	public SqlCriteria notEqNull(String key, Object value);

	public SqlCriteria gt(String key, Object value, boolean ignoreNull);

	public SqlCriteria gt(String key, Object value);

	public SqlCriteria gte(String key, Object value, boolean ignoreNull);

	public SqlCriteria gte(String key, Object value);

	public SqlCriteria lt(String key, Object value, boolean ignoreNull);

	public SqlCriteria lt(String key, Object value);

	public SqlCriteria lte(String key, Object value, boolean ignoreNull);

	public SqlCriteria lte(String key, Object value);

	public SqlCriteria between(String key, Object value0, Object value1, boolean ignoreNull);

	public SqlCriteria between(String key, Object value0, Object value1);

	public SqlCriteria notBetween(String key, Object value0, Object value1, boolean ignoreNull);

	public SqlCriteria notBetween(String key, Object value0, Object value1);

	public SqlCriteria in(String key, Collection<?> collection, boolean ignoreNull);

	public SqlCriteria in(String key, Collection<?> collection);

	public SqlCriteria notIn(String key, Collection<?> collection, boolean ignoreNull);

	public SqlCriteria notIn(String key, Collection<?> collection);

	public SqlCriteria isNull(String key);

	public SqlCriteria notNull(String key);
}
