package zr.monitor.bean.info;

import java.util.List;

public class ZRApiSettings implements Comparable<ZRApiSettings> {
	protected String packageName;
	protected boolean open;
	protected List<String> authoritys;

	public ZRApiSettings() {
	}

	public ZRApiSettings(String packageName, boolean open, List<String> authoritys) {
		this.packageName = packageName;
		this.open = open;
		this.authoritys = authoritys;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<String> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<String> authoritys) {
		this.authoritys = authoritys;
	}

	@Override
	public int compareTo(ZRApiSettings o) {
		return packageName.compareTo(o.packageName);
	}

}
