package zr.monitor.info;

import zr.monitor.bean.info.ZRAuthorityInfo;

public class ZRMethodVersionSettings extends ZRMethodSettings {
	protected volatile boolean topology;

	void set(boolean open, ZRAuthorityInfo[] authoritys, boolean topology) {
		super.set(open, authoritys);
		this.topology = topology;
	}

	public boolean isTopology() {
		return topology;
	}

}
