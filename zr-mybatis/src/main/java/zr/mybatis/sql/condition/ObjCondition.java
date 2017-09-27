package zr.mybatis.sql.condition;

import zr.mybatis.sql.SqlCriteria;
import zr.mybatis.sql.SqlWhere;

@SuppressWarnings("unchecked")
public abstract class ObjCondition<T extends ObjCondition<?>> {
	final SqlCriteria criteria;
	final FieldOps<T> fieldOps;
	SqlWhere where;

	public ObjCondition() {
		criteria = SqlCriteria.create();
		where = criteria.where();
		fieldOps = (FieldOps<T>) new FieldOps<>(this);
	}

	public final T or() {
		where = where.or();
		return (T) this;
	}

	public final SqlCriteria criteria() {
		return criteria;
	}

	protected final FieldOps<T> setKey(String key) {
		return fieldOps.setKey(key);
	}
}
