package zr.mybatis.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import v.common.helper.StrUtil;

public class SqlWhere {
	private static final List<Object> NULL_LIST = Arrays.asList(new Object[] { null });

	private static final String OPERATE_EQ = " =";
	private static final String OPERATE_NOT_EQ = " <>";
	private static final String OPERATE_GT = " >";
	private static final String OPERATE_LT = " <";
	private static final String OPERATE_GTE = " >=";
	private static final String OPERATE_LTE = " <=";
	private static final String OPERATE_LIKE = " LIKE";
	private static final String OPERATE_NOT_LIKE = " NOT LIKE";
	private static final String OPERATE_BETWEEN = " BETWEEN ";
	private static final String OPERATE_NOT_BETWEEN = " NOT BETWEEN ";
	private static final String OPERATE_IN = " IN";
	private static final String OPERATE_NOT_IN = " NOT IN";
	private static final String OPERATE_IS_NULL = " IS NULL";
	private static final String OPERATE_NOT_NULL = " IS NOT NULL";

	protected final SqlCriteria criteria;
	protected final List<SqlCondition> conditions;

	SqlWhere(SqlCriteria criteria) {
		this.criteria = criteria;
		this.conditions = new ArrayList<>(8);
	}

	private SqlWhere addCondition(String key, String operate, Object value, boolean ignoreNull) {
		if (ignoreNull && value == null)
			return this;
		return addCondition0(SqlCondition.KEY_NORMAL, key, operate, value, null);
	}

	private SqlWhere addCondition(String key, String operate, Object value0, Object value1, boolean ignoreNull) {
		if (ignoreNull && value0 == null && value1 == null)
			return this;
		return addCondition0(SqlCondition.KEY_DOUBLE, key, operate, value0, value1);
	}

	private SqlWhere addCondition(String key, String operate, Collection<?> collection, boolean ignoreNull) {
		if (collection == null) {
			if (ignoreNull)
				return this;
			else
				collection = NULL_LIST;
		} else if (collection.isEmpty())
			collection = NULL_LIST;
		return addCondition0(SqlCondition.KEY_COLLECTION, key, operate, collection, null);
	}

	private SqlWhere addCondition(final String key, final String operate) {
		return addCondition0(SqlCondition.KEY_RAW, key, operate, null, null);
	}

	private SqlWhere addCondition0(byte type, String key, String operate, Object value0, Object value1) {
		StringBuilder sb = new StringBuilder(key.length() + 2 + operate.length());
		sb.append('`').append(key).append('`').append(operate);
		if (conditions.isEmpty())
			criteria.addWhere(this);
		conditions.add(new SqlCondition(type, StrUtil.sbToString(sb), value0, value1));
		return this;
	}

	public SqlWhere or() {
		if (conditions.isEmpty())
			return this;
		return new SqlWhere(criteria);
	}

	public SqlWhere clear() {
		if (conditions.size() > 0) {
			criteria.popWhere();
			conditions.clear();
		}
		return this;
	}

	public SqlCriteria end() {
		return criteria;
	}

	public SqlWhere eq(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_EQ, value, ignoreNull);
	}

	public SqlWhere eq(String key, Object value) {
		return eq(key, value, false);
	}

	public SqlWhere eqNull(String key, Object value) {
		if (value == null)
			return isNull(key);
		return eq(key, value, false);
	}

	public SqlWhere notEq(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_NOT_EQ, value, ignoreNull);
	}

	public SqlWhere notEq(String key, Object value) {
		return notEq(key, value, false);
	}

	public SqlWhere notEqNull(String key, Object value) {
		if (value == null)
			return notNull(key);
		return notEq(key, value, false);
	}

	public SqlWhere gt(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_GT, value, ignoreNull);
	}

	public SqlWhere gt(String key, Object value) {
		return gt(key, value, false);
	}

	public SqlWhere gte(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_GTE, value, ignoreNull);
	}

	public SqlWhere gte(String key, Object value) {
		return gte(key, value, false);
	}

	public SqlWhere lt(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_LT, value, ignoreNull);
	}

	public SqlWhere lt(String key, Object value) {
		return lt(key, value, false);
	}

	public SqlWhere lte(String key, Object value, boolean ignoreNull) {
		return addCondition(key, OPERATE_LTE, value, ignoreNull);
	}

	public SqlWhere lte(String key, Object value) {
		return lte(key, value, false);
	}

	public SqlWhere like(String key, String value, boolean ignoreNull) {
		return addCondition(key, OPERATE_LIKE, value, ignoreNull);
	}

	public SqlWhere like(String key, String value) {
		return like(key, value, false);
	}

	public SqlWhere notLike(String key, String value, boolean ignoreNull) {
		return addCondition(key, OPERATE_NOT_LIKE, value, ignoreNull);
	}

	public SqlWhere notLike(String key, String value) {
		return notLike(key, value, false);
	}

	public SqlWhere between(String key, Object value0, Object value1, boolean ignoreNull) {
		return addCondition(key, OPERATE_BETWEEN, value0, value1, ignoreNull);
	}

	public SqlWhere between(String key, Object value0, Object value1) {
		return between(key, value0, value1, false);
	}

	public SqlWhere notBetween(String key, Object value0, Object value1, boolean ignoreNull) {
		return addCondition(key, OPERATE_NOT_BETWEEN, value0, value1, ignoreNull);
	}

	public SqlWhere notBetween(String key, Object value0, Object value1) {
		return notBetween(key, value0, value1, false);
	}

	public SqlWhere in(String key, Collection<?> collection, boolean ignoreNull) {
		return addCondition(key, OPERATE_IN, collection, ignoreNull);
	}

	public SqlWhere in(String key, Collection<?> collection) {
		return in(key, collection, false);
	}

	public SqlWhere notIn(String key, Collection<?> collection, boolean ignoreNull) {
		return addCondition(key, OPERATE_NOT_IN, collection, ignoreNull);
	}

	public SqlWhere notIn(String key, Collection<?> collection) {
		return notIn(key, collection, false);
	}

	public SqlWhere isNull(String key) {
		return addCondition(key, OPERATE_IS_NULL);
	}

	public SqlWhere notNull(String key) {
		return addCondition(key, OPERATE_NOT_NULL);
	}
}
