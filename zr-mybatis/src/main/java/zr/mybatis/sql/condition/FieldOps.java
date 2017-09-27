package zr.mybatis.sql.condition;

import java.util.Collection;

public class FieldOps<T extends ObjCondition<?, ?>> {
	protected final T condition;
	protected String key;

	public FieldOps(T condition) {
		this.condition = condition;
	}

	public final T eq(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.eq(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T eq(Object value) {
		return eq(value, false);
	}

	public final T eqNull(Object value) {
		if (value == null)
			return isNull();
		else
			return eq(value, false);
	}

	public final T notEq(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.notEq(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notEq(Object value) {
		return notEq(value, false);
	}

	public final T notEqNull(Object value) {
		if (value == null)
			return notNull();
		else
			return notEq(value, false);
	}

	public final T gt(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.gt(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T gt(Object value) {
		return gt(value, false);
	}

	public final T gte(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.gte(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T gte(Object value) {
		return gte(value, false);
	}

	public final T lt(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.lt(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T lt(Object value) {
		return lt(value, false);
	}

	public final T lte(Object value, boolean ignoreNull) {
		if (key != null) {
			condition.where.lte(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T lte(Object value) {
		return lte(value, false);
	}

	public final T like(String value, boolean ignoreNull) {
		if (key != null) {
			condition.where.like(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T like(String value) {
		return like(value, false);
	}

	public final T notLike(String value, boolean ignoreNull) {
		if (key != null) {
			condition.where.notLike(key, value, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notLike(String value) {
		return notLike(value, false);
	}

	public final T between(Object value0, Object value1, boolean ignoreNull) {
		if (key != null) {
			condition.where.between(key, value0, value1, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T between(Object value0, Object value1) {
		return between(value0, value1, false);
	}

	public final T notBetween(Object value0, Object value1, boolean ignoreNull) {
		if (key != null) {
			condition.where.notBetween(key, value0, value1, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notBetween(Object value0, Object value1) {
		return notBetween(value0, value1, false);
	}

	public final T in(Collection<?> collection, boolean ignoreNull) {
		if (key != null) {
			condition.where.in(key, collection, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T in(Collection<?> collection) {
		return in(collection, false);
	}

	public final T notIn(Collection<?> collection, boolean ignoreNull) {
		if (key != null) {
			condition.where.notIn(key, collection, ignoreNull);
			key = null;
		}
		return condition;
	}

	public final T notIn(Collection<?> collection) {
		return notIn(collection, false);
	}

	public final T isNull() {
		if (key != null) {
			condition.where.isNull(key);
			key = null;
		}
		return condition;
	}

	public final T notNull() {
		if (key != null) {
			condition.where.notNull(key);
			key = null;
		}
		return condition;
	}

	final FieldOps<T> setKey(String key) {
		this.key = key;
		return this;
	}
}
