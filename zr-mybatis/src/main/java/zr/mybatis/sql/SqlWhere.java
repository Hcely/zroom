package zr.mybatis.sql;

import java.util.Collection;

public interface SqlWhere {

	public SqlWhere or();

	public SqlWhere clear();

	public SqlCriteria end();

	public SqlWhere eq(String key, Object value, boolean ignoreNull);

	public SqlWhere eq(String key, Object value);

	public SqlWhere eqNull(String key, Object value);

	public SqlWhere notEq(String key, Object value, boolean ignoreNull);

	public SqlWhere notEq(String key, Object value);

	public SqlWhere notEqNull(String key, Object value);

	public SqlWhere gt(String key, Object value, boolean ignoreNull);

	public SqlWhere gt(String key, Object value);

	public SqlWhere gte(String key, Object value, boolean ignoreNull);

	public SqlWhere gte(String key, Object value);

	public SqlWhere lt(String key, Object value, boolean ignoreNull);

	public SqlWhere lt(String key, Object value);

	public SqlWhere lte(String key, Object value, boolean ignoreNull);

	public SqlWhere lte(String key, Object value);

	public SqlWhere like(String key, String value, boolean ignoreNull);

	public SqlWhere like(String key, String value);

	public SqlWhere notLike(String key, String value, boolean ignoreNull);

	public SqlWhere notLike(String key, String value);

	public SqlWhere between(String key, Object value0, Object value1, boolean ignoreNull);

	public SqlWhere between(String key, Object value0, Object value1);

	public SqlWhere notBetween(String key, Object value0, Object value1, boolean ignoreNull);

	public SqlWhere notBetween(String key, Object value0, Object value1);

	public SqlWhere in(String key, Collection<?> collection, boolean ignoreNull);

	public SqlWhere in(String key, Collection<?> collection);

	public SqlWhere notIn(String key, Collection<?> collection, boolean ignoreNull);

	public SqlWhere notIn(String key, Collection<?> collection);

	public SqlWhere isNull(String key);

	public SqlWhere notNull(String key);
}
