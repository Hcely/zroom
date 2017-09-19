package zr.mybatis.sql;

import java.util.Collection;

import v.common.helper.StrUtil;

public final class SqlHavingImpl implements SqlHaving {
	protected final SqlCriteriaImpl criteria;

	SqlHavingImpl(SqlCriteriaImpl criteria) {
		this.criteria = criteria;
	}

	private SqlCriteria setCondition(String key, String operate, Object value, boolean ignoreNull) {
		if (ignoreNull && value == null)
			return criteria;
		return setCondition0(SqlCondition.KEY_NORMAL, key, operate, value, null);
	}

	private SqlCriteria setCondition(String key, String operate, Object value0, Object value1, boolean ignoreNull) {
		if (ignoreNull && value0 == null && value1 == null)
			return criteria;
		return setCondition0(SqlCondition.KEY_DOUBLE, key, operate, value0, value1);
	}

	private SqlCriteria setCondition(String key, String operate, Collection<?> collection, boolean ignoreNull) {
		if (collection == null) {
			if (ignoreNull)
				return criteria;
			else
				collection = SqlWhereImpl.NULL_LIST;
		} else if (collection.isEmpty())
			collection = SqlWhereImpl.NULL_LIST;
		return setCondition0(SqlCondition.KEY_COLLECTION, key, operate, collection, null);
	}

	private SqlCriteria setCondition(final String key, final String operate) {
		return setCondition0(SqlCondition.KEY_RAW, key, operate, null, null);
	}

	private SqlCriteria setCondition0(byte type, String key, String operate, Object value0, Object value1) {
		StringBuilder sb = new StringBuilder(key.length() + 2 + operate.length());
		sb.append('`').append(key).append('`').append(operate);
		criteria.having = new SqlCondition(type, StrUtil.sbToString(sb), value0, value1);
		return criteria;
	}

	@Override
	public SqlHaving clear() {
		criteria.having = null;
		return this;
	}

	@Override
	public SqlCriteria end() {
		return criteria;
	}

	@Override
	public SqlCriteria eq(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_EQ, value, ignoreNull);
	}

	@Override
	public SqlCriteria eq(String key, Object value) {
		return eq(key, value, false);
	}

	@Override
	public SqlCriteria eqNull(String key, Object value) {
		if (value == null)
			return isNull(key);
		return eq(key, value, false);
	}

	@Override
	public SqlCriteria notEq(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_NOT_EQ, value, ignoreNull);
	}

	@Override
	public SqlCriteria notEq(String key, Object value) {
		return notEq(key, value, false);
	}

	@Override
	public SqlCriteria notEqNull(String key, Object value) {
		if (value == null)
			return notNull(key);
		return notEq(key, value, false);
	}

	@Override
	public SqlCriteria gt(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_GT, value, ignoreNull);
	}

	@Override
	public SqlCriteria gt(String key, Object value) {
		return gt(key, value, false);
	}

	@Override
	public SqlCriteria gte(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_GTE, value, ignoreNull);
	}

	@Override
	public SqlCriteria gte(String key, Object value) {
		return gte(key, value, false);
	}

	@Override
	public SqlCriteria lt(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_LT, value, ignoreNull);
	}

	@Override
	public SqlCriteria lt(String key, Object value) {
		return lt(key, value, false);
	}

	@Override
	public SqlCriteria lte(String key, Object value, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_LTE, value, ignoreNull);
	}

	@Override
	public SqlCriteria lte(String key, Object value) {
		return lte(key, value, false);
	}

	@Override
	public SqlCriteria between(String key, Object value0, Object value1, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_BETWEEN, value0, value1, ignoreNull);
	}

	@Override
	public SqlCriteria between(String key, Object value0, Object value1) {
		return between(key, value0, value1, false);
	}

	@Override
	public SqlCriteria notBetween(String key, Object value0, Object value1, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_NOT_BETWEEN, value0, value1, ignoreNull);
	}

	@Override
	public SqlCriteria notBetween(String key, Object value0, Object value1) {
		return notBetween(key, value0, value1, false);
	}

	@Override
	public SqlCriteria in(String key, Collection<?> collection, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_IN, collection, ignoreNull);
	}

	@Override
	public SqlCriteria in(String key, Collection<?> collection) {
		return in(key, collection, false);
	}

	@Override
	public SqlCriteria notIn(String key, Collection<?> collection, boolean ignoreNull) {
		return setCondition(key, SqlWhereImpl.OPERATE_NOT_IN, collection, ignoreNull);
	}

	@Override
	public SqlCriteria notIn(String key, Collection<?> collection) {
		return notIn(key, collection, false);
	}

	@Override
	public SqlCriteria isNull(String key) {
		return setCondition(key, SqlWhereImpl.OPERATE_IS_NULL);
	}

	@Override
	public SqlCriteria notNull(String key) {
		return setCondition(key, SqlWhereImpl.OPERATE_NOT_NULL);
	}
}
