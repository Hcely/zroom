package zr.monitor.bean.info.vo;

import zr.monitor.bean.info.ZRServiceInfo;

public class ZRServiceInfoVo extends ZRServiceInfo {
	protected boolean open;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
