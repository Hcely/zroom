package zr.mybatis.info;

public class SortField {
	protected final String name;
	protected final boolean asc;

	public SortField(String name, boolean asc) {
		this.name = name;
		this.asc = asc;
	}

	public String getName() {
		return name;
	}

	public boolean isAsc() {
		return asc;
	}

}
