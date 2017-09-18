package zr.mybatis.sql;

public class SqlCondition {
	public static final byte KEY_NORMAL = 1;
	public static final byte KEY_DOUBLE = 2;
	public static final byte KEY_COLLECTION = 3;
	public static final byte KEY_RAW = 4;

	protected final byte type;
	protected final String key;
	protected final Object value0;
	protected final Object value1;

	public SqlCondition(byte type, String key, Object value0, Object value1) {
		this.type = type;
		this.key = key;
		this.value0 = value0;
		this.value1 = value1;
	}

	public byte getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public Object getValue0() {
		return value0;
	}

	public Object getValue1() {
		return value1;
	}

}
