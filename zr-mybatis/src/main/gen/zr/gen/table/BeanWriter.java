package zr.gen.table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;

import v.common.helper.StrUtil;
import zr.mybatis.annotation.IncColumn;
import zr.mybatis.annotation.KeyColumn;

public class BeanWriter {
	public static final String DEF_OUTPUT_FOLDER = "./output";
	protected final String packageName;
	protected File outputFolder;
	protected BeanNameHandler nameHandler;
	protected boolean nativeClass;
	protected boolean smallAsInt;
	protected boolean instanceCreator;

	public BeanWriter(String packageName) {
		this(packageName, new File(DEF_OUTPUT_FOLDER), DefBeanNameHandler.INSTANCE, false, true, false);
	}

	public BeanWriter(String packageName, File outputFolder, BeanNameHandler nameHandler, boolean nativeClass,
			boolean smallAsInt, boolean instanceCreator) {
		this.packageName = packageName;
		this.outputFolder = outputFolder;
		this.nameHandler = nameHandler;
		this.nativeClass = nativeClass;
		this.smallAsInt = smallAsInt;
		this.instanceCreator = false;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setNameHandler(BeanNameHandler nameHandler) {
		this.nameHandler = nameHandler;
	}

	public void setNativeClass(boolean nativeClass) {
		this.nativeClass = nativeClass;
	}

	public void setSmallAsInt(boolean smallAsInt) {
		this.smallAsInt = smallAsInt;
	}

	public void write(TableInfo table) throws IOException {
		StringBuilder sb = new StringBuilder(1024 * 8);
		String beanName = nameHandler == null ? DefBeanNameHandler.INSTANCE.getBeanName(packageName)
				: nameHandler.getBeanName(table.tableName);
		writePackage(sb);
		writeImports(sb, table);
		writeBeanStart(sb, beanName);
		writeMemberParams(sb, table);
		writePostConstruct(sb, beanName);
		writeCloneMethod(sb, beanName);
		writeGetSets(sb, table, beanName);
		writeBeanEnd(sb);
		if (outputFolder == null)
			outputFolder = new File(DEF_OUTPUT_FOLDER);
		if (!outputFolder.exists())
			outputFolder.mkdirs();
		FileOutputStream out = new FileOutputStream(new File(outputFolder, beanName + ".java"));
		out.write(sb.toString().getBytes());
		out.close();
	}

	private void writePackage(StringBuilder sb) {
		sb.append("package ").append(packageName).append(";\n\n");
	}

	private void writeImports(StringBuilder sb, TableInfo table) {
		List<String> list = getImports(table);
		for (String e : list)
			sb.append("import ").append(e).append(";\n");
		sb.append('\n');
	}

	private void writeBeanStart(StringBuilder sb, String beanName) {
		sb.append("public class ").append(beanName).append(" implements Serializable, Cloneable {\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;\n");
		if (instanceCreator) {
			sb.append("\tprivate static final ").append(beanName).append(" INSTANCE = new ").append(beanName)
					.append("();\n\n");
			sb.append("\tpublic static final ").append(beanName).append(" create() {\n");
			sb.append("\t\treturn INSTANCE.clone();\n\t}\n");
		}
		sb.append("\n");
	}

	private void writeMemberParams(StringBuilder sb, TableInfo table) {

		for (ColumnInfo col : table.columns) {
			if (col.inc)
				sb.append("\t@IncColumn");
			if (col.pri)
				sb.append("\t@KeyColumn");
			sb.append("\tprotected ").append(getType(col.type)).append(" ").append(col.name).append(";\n");
		}
		sb.append('\n');
	}

	private void writePostConstruct(StringBuilder sb, String beanName) {
		sb.append("\tpublic ").append(beanName).append("() {\n\t}\n\n");
	}

	private void writeCloneMethod(StringBuilder sb, String beanName) {
		sb.append("\t@Override\n\tpublic ").append(beanName).append(" clone() {\n\t\ttry {\n\t\t\treturn (")
				.append(beanName)
				.append(") super.clone();\n\t\t} catch (CloneNotSupportedException e) {\n\t\t\tthrow new RuntimeException(e);\n\t\t}\t}\n\n");
	}

	private void writeGetSets(StringBuilder sb, TableInfo table, String beanName) {
		for (ColumnInfo col : table.columns)
			writeGetSet(sb, beanName, col);
	}

	private void writeGetSet(StringBuilder sb, String beanName, ColumnInfo column) {
		String typeName = getType(column.type);
		String uName = buildUName(column.name);
		sb.append("\tpublic ").append(typeName).append(" get").append(uName).append("() {\n");
		sb.append("\t\treturn ").append(column.name).append(";\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic ").append(beanName).append(" set").append(uName).append("(").append(typeName).append(" ")
				.append(column.name).append(") {\n");
		sb.append("\t\tthis.").append(column.name).append(" = ").append(column.name).append(";\n");
		sb.append("\t}\n\n");
	}

	private void writeBeanEnd(StringBuilder sb) {
		sb.append('}');
	}

	protected final List<String> getImports(TableInfo table) {
		IdentityHashMap<Class<?>, Void> map = new IdentityHashMap<>();
		map.put(Serializable.class, null);
		for (ColumnInfo col : table.columns) {
			if (col.type == ColumnType.DATE)
				map.put(Date.class, null);
			if (col.inc)
				map.put(IncColumn.class, null);
			if (col.pri)
				map.put(KeyColumn.class, null);
		}
		List<String> hr = new ArrayList<>(map.size());
		for (Class<?> e : map.keySet())
			hr.add(e.getName());
		Collections.sort(hr);
		return hr;
	}

	protected String getType(ColumnType type) {
		switch (type) {
		case BYTE:
		case SHORT:
			if (nativeClass)
				return type.nativeName;
			else if (smallAsInt)
				return ColumnType.INT.typeName;
			else
				return type.typeName;
		case INT:
			return nativeClass ? type.nativeName : type.typeName;
		case LONG:
			return nativeClass ? type.nativeName : type.typeName;
		case FLOAT:
			return nativeClass ? type.nativeName : type.typeName;
		case DOUBLE:
			return nativeClass ? type.nativeName : type.typeName;
		case DATE:
			return type.typeName;
		case STR:
		default:
			return type.typeName;
		}
	}

	private static final String buildUName(String name) {
		StringBuilder sb = new StringBuilder(name.length());
		sb.append(name);
		sb.setCharAt(0, Character.toUpperCase(name.charAt(0)));
		return StrUtil.sbToString(sb);
	}

}
