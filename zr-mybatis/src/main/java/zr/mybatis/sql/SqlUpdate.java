package zr.mybatis.sql;

public final class SqlUpdate {
	protected final boolean raw;
	protected final String key;
	protected final Object value;

	SqlUpdate(boolean raw, String key, Object value) {
		this.raw = raw;
		this.key = key;
		this.value = value;
	}

	public boolean isRaw() {
		return raw;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

}
