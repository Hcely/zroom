package zr.monitor.statistic;

import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;
import zr.monitor.util.ZRApiCounts;

public interface ZRStatisticHandler {
	public void onReqTask(ZRStatistic tasks);

	public void onApiCount(String machineIp, String serverId, String serviceId, ZRApiCounts count);

	public void onMachineStatus(ZRMachineStatus status);

	public void onServerStatus(ZRServerStatus status);

}
