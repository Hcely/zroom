package zr.monitor.bean.info.vo;

import zr.monitor.bean.info.ZRMachineInfo;

public class ZRMachineInfoVo extends ZRMachineInfo {
	protected boolean online;
	protected boolean open;

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
