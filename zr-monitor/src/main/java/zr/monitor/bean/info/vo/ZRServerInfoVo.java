package zr.monitor.bean.info.vo;

import zr.monitor.bean.info.ZRServerInfo;

public class ZRServerInfoVo extends ZRServerInfo {
	protected boolean open;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
