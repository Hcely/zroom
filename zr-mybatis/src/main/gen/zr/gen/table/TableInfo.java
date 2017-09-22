package zr.gen.table;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	protected final String tableName;
	protected final List<ColumnInfo> columns;

	public TableInfo(String tableName) {
		this.tableName = tableName;
		this.columns = new ArrayList<>(16);
	}

	public void addColumn(final String name, final boolean pri, final boolean inc, final ColumnType type) {
		addColumn(new ColumnInfo(name, pri, inc, type));
	}

	public void addColumn(ColumnInfo col) {
		columns.add(col);
	}

	public String getTableName() {
		return tableName;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

}
