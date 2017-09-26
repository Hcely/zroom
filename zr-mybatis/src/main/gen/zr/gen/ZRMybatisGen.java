package zr.gen;

import java.io.File;
import java.util.Map;

import zr.gen.db.DatabaseFactory;
import zr.gen.table.TableInfo;

public class ZRMybatisGen {
	protected final BeanWriter writer;
	protected DatabaseFactory factory;

	public ZRMybatisGen(String packageName) {
		this(packageName, null);
	}

	public ZRMybatisGen(String packageName, File outputFolder) {
		writer = new BeanWriter(packageName);
		writer.setOutputFolder(outputFolder);
	}

	public void setOutputFolder(File outputFolder) {
		writer.setOutputFolder(outputFolder);
	}

	public void setOutputFolder(String outputFolder) {
		writer.setOutputFolder(outputFolder);
	}

	public void setNameHandler(BeanNameHandler nameHandler) {
		writer.setNameHandler(nameHandler);
	}

	public void setNativeClass(boolean nativeClass) {
		writer.setNativeClass(nativeClass);
	}

	public void setSmallAsInt(boolean smallAsInt) {
		writer.setSmallAsInt(smallAsInt);
	}

	public void setInstanceCreator(boolean instanceCreator) {
		writer.setInstanceCreator(instanceCreator);
	}

	public void setFactory(DatabaseFactory factory) {
		this.factory = factory;
	}

	public void doOutput() {
		factory.init();
		writer.init();
		Map<String, TableInfo> tables = factory.getTables();
		for (TableInfo table : tables.values())
			writer.write(table);
	}
}
