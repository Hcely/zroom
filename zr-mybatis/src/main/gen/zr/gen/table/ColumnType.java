package zr.gen.table;

public enum ColumnType {
	BYTE("Byte", "byte"), //
	SHORT("Short", "short"), //
	INT("Integer", "int"), //
	LONG("Long", "long"), //
	FLOAT("Float", "float"), //
	DOUBLE("Double", "double"), //
	STRING("String", "String"), //
	DATE("Date", "Date");

	public final String typeName;
	public final String nativeName;

	private ColumnType(String typeName, String nativeName) {
		this.typeName = typeName;
		this.nativeName = nativeName;
	}
}
