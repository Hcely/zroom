package zr.mybatis;

public interface TableNameHandler {
	public String getTableName(Class<?> beanClazz);

	public static void toUnline(StringBuilder sb, String str) {
		sb.ensureCapacity(sb.length() + 4 + str.length());
		for (int i = 0, len = str.length(); i < len; ++i) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0)
					sb.append('_');
				c = Character.toLowerCase(c);
			}
			sb.append(c);
		}
	}
}
