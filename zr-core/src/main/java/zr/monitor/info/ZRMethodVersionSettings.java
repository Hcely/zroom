package zr.monitor.info;

import zr.monitor.bean.info.ZRAuthorityInfo;

public class ZRMethodVersionSettings extends ZRMethodSettings {
	protected volatile int topology;

	void set(boolean open, ZRAuthorityInfo[] authoritys, int topology) {
		super.set(open, authoritys);
		this.topology = topology;
	}

	public int getTopology() {
		return topology;
	}

}
