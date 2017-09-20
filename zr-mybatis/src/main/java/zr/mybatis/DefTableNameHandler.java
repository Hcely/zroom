package zr.mybatis;

public class DefTableNameHandler implements TableNameHandler {
	static final DefTableNameHandler INSTANCE = new DefTableNameHandler();

	@Override
	public String getTableName(Class<?> beanClazz) {
		String name = beanClazz.getSimpleName();
		if (name.endsWith("Entity") || name.endsWith("entity"))
			name = name.substring(0, name.length() - 6);
		else if (name.endsWith("Bean") || name.endsWith("bean"))
			name = name.substring(0, name.length() - 4);
		else if (name.endsWith("Domain") || name.endsWith("domain"))
			name = name.substring(0, name.length() - 6);
		StringBuilder sb = new StringBuilder(10 + name.length());
		sb.append("t_");
		TableNameHandler.toUnline(sb, name);
		return sb.toString();
	}
}
