package zr.mybatis.sql;

import java.util.Collection;

public class FieldOps<T extends ObjCriteria<?>> {
	protected final T condition;
	protected SqlWhere where;
	protected String key;

	public FieldOps(T condition) {
		this.condition = condition;
		this.where = condition.criteria.where();
	}

	final T or() {
		where = where.or();
		return condition;
	}

	public final T set(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.criteria.update(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T set(Object value) {
		if (key != null) {
			condition.criteria.update(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T eq(Object value, boolean ignoreNull) {
		if (key != null) {
			where.eq(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T eq(Object value) {
		if (key != null) {
			where.eq(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T eqNull(Object value) {
		if (value == null)
			return isNull();
		else
			return eq(value, false);
	}

	public final T notEq(Object value, boolean ignoreNull) {
		if (key != null) {
			where.notEq(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notEq(Object value) {
		if (key != null) {
			where.notEq(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T notEqNull(Object value) {
		if (value == null)
			return notNull();
		else
			return notEq(value, false);
	}

	public final T gt(Object value, boolean ignoreNull) {
		if (key != null) {
			where.gt(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T gt(Object value) {
		if (key != null) {
			where.gt(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T gte(Object value, boolean ignoreNull) {
		if (key != null) {
			where.gte(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T gte(Object value) {
		if (key != null) {
			where.gte(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T lt(Object value, boolean ignoreNull) {
		if (key != null) {
			where.lt(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T lt(Object value) {
		if (key != null) {
			where.lt(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T lte(Object value, boolean ignoreNull) {
		if (key != null) {
			where.lte(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T lte(Object value) {
		if (key != null) {
			where.lte(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T like(String value, boolean ignoreNull) {
		if (key != null) {
			where.like(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T like(String value) {
		if (key != null) {
			where.like(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T notLike(String value, boolean ignoreNull) {
		if (key != null) {
			where.notLike(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notLike(String value) {
		if (key != null) {
			where.notLike(key, value, false);
			key = null;
		}
		return condition;
	}

	public final T between(Object value0, Object value1, boolean ignoreNull) {
		if (key != null) {
			where.between(key, value0, value1, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T between(Object value0, Object value1) {
		if (key != null) {
			where.between(key, value0, value1, false);
			key = null;
		}
		return condition;
	}

	public final T notBetween(Object value0, Object value1, boolean ignoreNull) {
		if (key != null) {
			where.notBetween(key, value0, value1, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notBetween(Object value0, Object value1) {
		if (key != null) {
			where.notBetween(key, value0, value1, false);
			key = null;
		}
		return condition;
	}

	public final T in(Collection<?> collection, boolean ignoreNull) {
		if (key != null) {
			where.in(key, collection, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T in(Collection<?> collection) {
		if (key != null) {
			where.in(key, collection, false);
			key = null;
		}
		return condition;
	}

	public final T notIn(Collection<?> collection, boolean ignoreNull) {
		if (key != null) {
			where.notIn(key, collection, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notIn(Collection<?> collection) {
		if (key != null) {
			where.notIn(key, collection, false);
			key = null;
		}
		return condition;
	}

	public final T isNull() {
		if (key != null) {
			where.isNull(key);
			key = null;
		}
		return condition;
	}

	public final T notNull() {
		if (key != null) {
			where.notNull(key);
			key = null;
		}
		return condition;
	}

	final FieldOps<T> setKey(String key) {
		this.key = key;
		return this;
	}
}
