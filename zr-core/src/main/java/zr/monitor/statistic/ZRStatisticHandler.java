package zr.monitor.statistic;

import zr.monitor.bean.result.ZRApiCountResult;
import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.bean.result.ZRTopologyResult;
import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

public interface ZRStatisticHandler {
	public void onRequest(String machineIp, String serverId, String serviceId, ZRRequestResult result);

	public void onTopology(String machineIp, String serverId, String serviceId, ZRTopologyResult result);

	public void onApiCount(String machineIp, String serverId, String serviceId, ZRApiCountResult result);

	public void onMachineStatus(ZRMachineStatus status);

	public void onServerStatus(ZRServerStatus status);

}
