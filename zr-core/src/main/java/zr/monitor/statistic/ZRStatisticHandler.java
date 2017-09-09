package zr.monitor.statistic;

import java.util.List;

import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.bean.result.ZRTopologyResult;
import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

public interface ZRStatisticHandler {
	public void onRequest(String machineIp, String serverId, String serviceId, ZRRequestResult reuqest);

	public void onTopology(String machineIp, String serverId, String serviceId, ZRTopologyResult topology);

	public void onApiCount(String machineIp, String serverId, String serviceId, long startTime, long endTime,
			List<ZRApiCount> counts);

	public void onMachineStatus(ZRMachineStatus status);

	public void onServerStatus(ZRServerStatus status);

}
