package zr.gen;

public interface BeanNameHandler {

	public static void toULCase(StringBuilder sb, String str) {
		sb.ensureCapacity(sb.length() + str.length());
		boolean b = true;
		char c;
		for (int i = 0, len = str.length(); i < len; ++i)
			if ((c = str.charAt(i)) == '_')
				b = true;
			else {
				if (b) {
					b = false;
					c = Character.toUpperCase(c);
				}
				sb.append(c);
			}
	}

	public String getBeanName(String tableName);
}
