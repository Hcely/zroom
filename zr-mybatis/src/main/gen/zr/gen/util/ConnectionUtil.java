package zr.gen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionUtil {
	private static volatile boolean loadMysqlDriver;

	private static final void checkMysqlDriver() {
		if (loadMysqlDriver)
			return;
		synchronized (ConnectionUtil.class) {
			if (loadMysqlDriver)
				return;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				loadMysqlDriver = true;
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static final Connection getMysqlConn(String host, int port, String database, String username,
			String password) throws SQLException {
		return getMysqlConn(host, port, database, username, password, null);
	}

	public static final Connection getMysqlConn(String host, int port, String database, String username,
			String password, Map<String, Object> params) throws SQLException {
		checkMysqlDriver();
		StringBuilder sb = new StringBuilder(256);
		sb.append("jdbc:mysql://").append(host).append(':').append(port).append('/').append(database).append('?');
		if (params == null)
			params = new HashMap<>();
		if (!params.containsKey("serverTimezone"))
			params.put("serverTimezone", "UTC");
		for (Entry<String, Object> e : params.entrySet())
			sb.append(e.getKey()).append('=').append(e.getValue()).append('&');
		String url = sb.substring(0, sb.length() - 1);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
