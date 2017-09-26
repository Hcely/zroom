package zr.gen.start;

import java.io.File;

import zr.gen.ZRMybatisGen;
import zr.gen.db.MysqlDatabaseFactory;

public class Start {
	public static final String host = "192.168.3.6";
	public static final int port = 3306;
	public static final String db = "elifeDb";
	public static final String username = "root";
	public static final String password = "Root_123456";
	public static final String outputFolder = "d:/beans";
	public static final String packageName = "com.elife.bean";

	public static void main(String[] args) {
		ZRMybatisGen gen = new ZRMybatisGen(packageName, new File(outputFolder));
		MysqlDatabaseFactory factory = new MysqlDatabaseFactory(host, port, db, username, password);
		gen.setFactory(factory);
		gen.setInstanceCreator(true);
		gen.doOutput();
	}
}
