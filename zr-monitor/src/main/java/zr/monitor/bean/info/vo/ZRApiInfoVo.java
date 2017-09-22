package zr.monitor.bean.info.vo;

import java.util.List;

import zr.monitor.bean.info.ZRApiInfo;

public class ZRApiInfoVo extends ZRApiInfo {
	protected List<String> authoritys;
	protected boolean open;
	protected int topology;

	public List<String> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<String> authoritys) {
		this.authoritys = authoritys;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getTopology() {
		return topology;
	}

	public void setTopology(int topology) {
		this.topology = topology;
	}

}
