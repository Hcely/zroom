package zr.gen.table;

public class DefBeanNameHandler implements BeanNameHandler {
	public static final DefBeanNameHandler INSTANCE = new DefBeanNameHandler();

	@Override
	public String getBeanName(String tableName) {
		if (tableName.startsWith("t_"))
			tableName = tableName.substring(2);
		else if (tableName.startsWith("table_"))
			tableName = tableName.substring(6);
		StringBuilder sb = new StringBuilder(tableName.length() + 6);
		BeanNameHandler.toULCase(sb, tableName);
		sb.append("Entity");
		return sb.toString();
	}

}
