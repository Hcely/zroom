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
import zr.gen.table.ColumnInfo;
import zr.gen.table.ColumnType;
import zr.gen.table.DefBeanNameHandler;
import zr.gen.table.TableInfo;
import zr.mybatis.annotation.IncColumn;
import zr.mybatis.annotation.KeyColumn;
import zr.mybatis.sql.condition.FieldOps;
import zr.mybatis.sql.condition.ObjCondition;

public class BeanWriter implements Initializable {
	public static final String DEF_OUTPUT_FOLDER = "./output";
	protected final String packageName;
	protected File outputFolder;
	protected BeanNameHandler nameHandler;
	protected boolean nativeClass;
	protected boolean smallAsInt;
	protected boolean instanceCreator;
	protected boolean useAnnotation;
	protected boolean conditionOps;

	public BeanWriter(String packageName) {
		this(packageName, null, DefBeanNameHandler.INSTANCE, false, true, false, true, true);
	}

	public BeanWriter(String packageName, File outputFolder, BeanNameHandler nameHandler, boolean nativeClass,
			boolean smallAsInt, boolean instanceCreator, boolean useAnnotation, boolean conditionOps) {
		this.packageName = packageName;
		this.outputFolder = outputFolder;
		this.nameHandler = nameHandler;
		this.nativeClass = nativeClass;
		this.smallAsInt = smallAsInt;
		this.instanceCreator = instanceCreator;
		this.useAnnotation = useAnnotation;
		this.conditionOps = conditionOps;
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

	public void setConditionOps(boolean conditionOps) {
		this.conditionOps = conditionOps;
	}

	@Override
	public void init() {
		if (nameHandler == null)
			nameHandler = DefBeanNameHandler.INSTANCE;
		outputFolder = buildOutputFoler(outputFolder, packageName);
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
		if (conditionOps)
			writeConditionOps(sb, table, beanName);
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
		char c = 0;
		for (String e : list) {
			if (c == 0)
				c = e.charAt(0);
			else if (c != e.charAt(0)) {
				c = e.charAt(0);
				sb.append('\n');
			}
			sb.append("import ").append(e).append(";\n");
		}
		sb.append('\n');
	}

	private void writeBeanStart(StringBuilder sb, String beanName) {
		sb.append("public class ").append(beanName).append(" implements Serializable, Cloneable {\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;\n\n");
		if (instanceCreator) {
			sb.append("\tprivate static final ").append(beanName).append(" INSTANCE = new ").append(beanName)
					.append("();\n\n");
			sb.append("\tpublic static final ").append(beanName).append(" create() {\n");
			sb.append("\t\treturn INSTANCE.clone();\n\t}\n\n");
		}
		if (conditionOps)
			sb.append("\tpublic static final Condition condition() {\n\t\treturn new Condition();\n\t}\n\n");
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
		String name = column.getName();
		int len;
		sb.append("\tpublic ").append(typeName).append(" get");
		len = sb.length();
		sb.append(name).append("() {\n");
		sb.setCharAt(len, Character.toUpperCase(name.charAt(0)));
		sb.append("\t\treturn ").append(name).append(";\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic ").append(beanName).append(" set");
		len = sb.length();
		sb.append(name).append("(").append(typeName).append(" ").append(name).append(") {\n");
		sb.setCharAt(len, Character.toUpperCase(name.charAt(0)));
		sb.append("\t\tthis.").append(name).append(" = ").append(name).append(";\n");
		sb.append("\t\treturn this;\n");
		sb.append("\t}\n\n");
	}

	private void writeConditionOps(StringBuilder sb, TableInfo table, String beanName) {
		sb.append("\tpublic static final class Condition extends ObjCondition<Condition, ").append(beanName)
				.append("> {\n\n");
		sb.append("\t\tprivate Condition() {\n\t\t}\n\n");
		for (ColumnInfo col : table.getColumns()) {
			String name = col.getName();
			sb.append("\t\tpublic final FieldOps<Condition> ").append(name).append("() {\n");
			sb.append("\t\t\treturn setKey(\"").append(name).append("\");\n");
			sb.append("\t\t}\n\n");
		}
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
		if (conditionOps) {
			map.put(FieldOps.class, null);
			map.put(ObjCondition.class, null);
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

	public static File buildOutputFoler(File folder, String packageName) {
		if (folder == null)
			folder = new File(DEF_OUTPUT_FOLDER);
		String path = packageName.replace('.', '/');
		folder = new File(folder, path);
		if (!folder.exists())
			folder.mkdirs();
		return folder;
	}

}
