package zr.monitor.info;

import zr.monitor.bean.info.ZRAuthorityInfo;

public class ZRMethodSettings {
	protected volatile boolean open;
	protected volatile ZRAuthorityInfo[] authoritys;

	void set(boolean open, ZRAuthorityInfo[] authoritys) {
		this.open = open;
		this.authoritys = authoritys;
	}

	public ZRAuthorityInfo[] getAuthoritys() {
		return authoritys;
	}

	public boolean isOpen() {
		return open;
	}

}
