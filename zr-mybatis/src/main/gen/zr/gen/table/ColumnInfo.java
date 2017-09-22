package zr.gen.table;

public class ColumnInfo {

	protected final String name;
	protected final boolean pri;
	protected final boolean inc;
	protected final ColumnType type;

	public ColumnInfo(String name, boolean pri, boolean inc, ColumnType type) {
		this.name = name;
		this.pri = pri;
		this.inc = inc;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isPri() {
		return pri;
	}

	public boolean isInc() {
		return inc;
	}

	public ColumnType getType() {
		return type;
	}

}
