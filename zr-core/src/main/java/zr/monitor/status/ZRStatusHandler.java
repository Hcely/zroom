package zr.monitor.status;

import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

public interface ZRStatusHandler {
	public void onMachineStatus(ZRMachineStatus status);

	public void onServerStatus(ZRServerStatus status);
}
