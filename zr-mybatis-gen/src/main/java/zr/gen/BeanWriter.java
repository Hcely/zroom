package zr.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;

import v.Initializable;
import v.common.helper.StrUtil;
import zr.gen.table.ColumnInfo;
import zr.gen.table.ColumnType;
import zr.gen.table.DefBeanNameHandler;
import zr.gen.table.TableInfo;
import zr.mybatis.annotation.IncColumn;
import zr.mybatis.annotation.KeyColumn;

public class BeanWriter implements Initializable {
	public static final String DEF_OUTPUT_FOLDER = "./output";
	protected final String packageName;
	protected File outputFolder;
	protected BeanNameHandler nameHandler;
	protected boolean nativeClass;
	protected boolean smallAsInt;
	protected boolean instanceCreator;
	protected boolean useAnnotation;

	public BeanWriter(String packageName) {
		this(packageName, null, DefBeanNameHandler.INSTANCE, false, true, false);
	}

	public BeanWriter(String packageName, File outputFolder, BeanNameHandler nameHandler, boolean nativeClass,
			boolean smallAsInt, boolean instanceCreator) {
		this.packageName = packageName;
		this.outputFolder = outputFolder;
		this.nameHandler = nameHandler;
		this.nativeClass = nativeClass;
		this.smallAsInt = smallAsInt;
		this.instanceCreator = false;
		this.useAnnotation = true;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		setOutputFolder(new File(outputFolder));
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

	public void setInstanceCreator(boolean instanceCreator) {
		this.instanceCreator = instanceCreator;
	}

	public void setUseAnnotation(boolean useAnnotation) {
		this.useAnnotation = useAnnotation;
	}

	@Override
	public void init() {
		if (nameHandler == null)
			nameHandler = DefBeanNameHandler.INSTANCE;
		if (outputFolder == null)
			outputFolder = new File(DEF_OUTPUT_FOLDER);
		String path = packageName.replace('.', '/');
		outputFolder = new File(outputFolder, path);
		if (!outputFolder.exists())
			outputFolder.mkdirs();
	}

	public void write(TableInfo table) {
		StringBuilder sb = new StringBuilder(1024 * 8);
		String beanName = nameHandler.getBeanName(table.getTableName());
		writePackage(sb);
		writeImports(sb, table);
		writeBeanStart(sb, beanName);
		writeMemberParams(sb, table);
		writePostConstruct(sb, beanName);
		writeCloneMethod(sb, beanName);
		writeGetSets(sb, table, beanName);
		writeBeanEnd(sb);
		try {
			FileOutputStream out = new FileOutputStream(new File(outputFolder, beanName + ".java"));
			out.write(sb.toString().getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
		for (ColumnInfo col : table.getColumns()) {
			if (useAnnotation) {
				if (col.isInc())
					sb.append("\t@IncColumn\n");
				if (col.isPri())
					sb.append("\t@KeyColumn\n");
			}
			sb.append("\tprotected ").append(getType(col.getType())).append(" ").append(col.getName()).append(";\n");
		}
		sb.append('\n');
	}

	private void writePostConstruct(StringBuilder sb, String beanName) {
		sb.append("\tpublic ").append(beanName).append("() {\n\t}\n\n");
	}

	private void writeCloneMethod(StringBuilder sb, String beanName) {
		sb.append("\t@Override\n\tpublic ").append(beanName).append(" clone() {\n\t\ttry {\n\t\t\treturn (")
				.append(beanName)
				.append(") super.clone();\n\t\t} catch (CloneNotSupportedException e) {\n\t\t\tthrow new RuntimeException(e);\n\t\t}\n\t}\n\n");
	}

	private void writeGetSets(StringBuilder sb, TableInfo table, String beanName) {
		for (ColumnInfo col : table.getColumns())
			writeGetSet(sb, beanName, col);
	}

	private void writeGetSet(StringBuilder sb, String beanName, ColumnInfo column) {
		String typeName = getType(column.getType());
		String uName = buildUName(column.getName());
		sb.append("\tpublic ").append(typeName).append(" get").append(uName).append("() {\n");
		sb.append("\t\treturn ").append(column.getName()).append(";\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic ").append(beanName).append(" set").append(uName).append("(").append(typeName).append(" ")
				.append(column.getName()).append(") {\n");
		sb.append("\t\tthis.").append(column.getName()).append(" = ").append(column.getName()).append(";\n");
		sb.append("\t\treturn this;\n");
		sb.append("\t}\n\n");
	}

	private void writeBeanEnd(StringBuilder sb) {
		sb.append('}');
	}

	protected final List<String> getImports(TableInfo table) {
		IdentityHashMap<Class<?>, Void> map = new IdentityHashMap<>();
		map.put(Serializable.class, null);
		for (ColumnInfo col : table.getColumns()) {
			if (col.getType() == ColumnType.DATE)
				map.put(Date.class, null);
			if (useAnnotation) {
				if (col.isInc())
					map.put(IncColumn.class, null);
				if (col.isPri())
					map.put(KeyColumn.class, null);
			}
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
		case STRING:
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
