package zr.gen.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import v.common.helper.StrUtil;
import zr.gen.table.ColumnType;
import zr.gen.table.TableInfo;
import zr.gen.util.ConnectionUtil;

public class MysqlDatabaseFactory implements DatabaseFactory {
	public static final String DEF_HOST = "127.0.0.1";
	public static final int DEF_PORT = 3306;
	public static final String DEF_DB = "mysql";
	public static final String DEF_USERNAME = "root";

	public static final String SHOW_TABLES = "SHOW TABLES";
	public static final String SHOW_COLUMNS = "SHOW COLUMNS FROM `%s`";

	protected String host;
	protected int port;
	protected String db;
	protected String username;
	protected String password;
	protected Connection conn;

	public MysqlDatabaseFactory() {
		this(DEF_HOST, DEF_PORT, DEF_DB, DEF_USERNAME, null);
	}

	public MysqlDatabaseFactory(String host, int port, String db, String username, String password) {
		this.host = host;
		this.port = port;
		this.db = db;
		this.username = username;
		this.password = password;
		this.conn = null;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void init() {
		if (conn == null)
			try {
				conn = ConnectionUtil.getMysqlConn(host, port, db, username, password);
			} catch (SQLException e) {
				conn = null;
				throw new RuntimeException(e);
			}
	}

	@Override
	public void destory() {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conn = null;
		}
	}

	@Override
	public Map<String, TableInfo> getTables() {
		LinkedHashMap<String, TableInfo> hr = new LinkedHashMap<>();
		try {
			showTables(hr);
			for (TableInfo e : hr.values())
				showColumns(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return hr;
	}

	private void showTables(final LinkedHashMap<String, TableInfo> hr) throws SQLException {
		Statement sm = conn.createStatement();
		ResultSet rs = sm.executeQuery(SHOW_TABLES);
		while (rs.next()) {
			String tableName = rs.getString(1);
			hr.put(tableName, new TableInfo(tableName));
		}
		rs.close();
		sm.close();
	}

	private void showColumns(final TableInfo table) throws SQLException {
		Statement sm = conn.createStatement();
		ResultSet rs = sm.executeQuery(String.format(SHOW_COLUMNS, table.getTableName()));
		while (rs.next()) {
			String field = rs.getString(1);
			String type = rs.getString(2);
			String key = rs.getString(4);
			String extra = rs.getString(6);
			ColumnType ctype = getType(type);
			boolean pri = isPri(key);
			boolean inc = isAutoIncrease(extra);
			table.addColumn(field, pri, inc, ctype);
		}
	}

	public static final boolean isPri(String value) {
		if (StrUtil.isEmpty(value))
			return false;
		value = value.toLowerCase();
		return value.contains("pri");
	}

	public static final boolean isAutoIncrease(String value) {
		if (StrUtil.isEmpty(value))
			return false;
		value = value.toLowerCase();
		return value.contains("auto_increment");
	}

	public static final ColumnType getType(String type) {
		type = type.toLowerCase();
		if (type.startsWith("varchar") || type.startsWith("char") || type.startsWith("tinytext")
				|| type.startsWith("text") || type.startsWith("mediumtext") || type.startsWith("longtext"))
			return ColumnType.STRING;
		if (type.startsWith("date") || type.startsWith("time") || type.startsWith("year"))
			return ColumnType.DATE;
		if (type.startsWith("numeric") || type.startsWith("decimal") || type.startsWith("real")
				|| type.startsWith("double"))
			return ColumnType.DOUBLE;
		if (type.startsWith("float"))
			return ColumnType.FLOAT;
		if (type.startsWith("bigint"))
			return ColumnType.LONG;
		if (type.startsWith("mediumint"))
			return ColumnType.LONG;
		if (type.startsWith("int"))
			return ColumnType.INT;
		if (type.startsWith("smallint"))
			return ColumnType.SHORT;
		if (type.startsWith("tinyint"))
			return ColumnType.BYTE;
		return null;
	}

}
