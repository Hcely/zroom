package zr.mybatis.sql;

@SuppressWarnings("unchecked")
public abstract class ObjCriteria<T extends ObjCriteria<?>> {
	final SqlCriteria criteria;
	final FieldOps<T> fieldOps;

	public ObjCriteria() {
		criteria = SqlCriteria.create();
		fieldOps = (FieldOps<T>) new FieldOps<>(this);
	}

	public final T or() {
		return fieldOps.or();
	}

	public final SqlCriteria criteria() {
		return criteria;
	}

	protected final FieldOps<T> setKey(String key) {
		return fieldOps.setKey(key);
	}
}
