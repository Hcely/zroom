package zr.monitor.statistic;

import zr.monitor.bean.result.ZRApiCountResult;
import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.bean.result.ZRTopologyResult;
import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

public interface ZRStatisticHandler {
	public static final ZRStatisticHandler DEF = new ZRStatisticHandler() {
		@Override
		public void onTopology(String machineIp, String serverId, String serviceId, ZRTopologyResult result) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServerStatus(ZRServerStatus status) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequest(String machineIp, String serverId, String serviceId, ZRRequestResult result) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMachineStatus(ZRMachineStatus status) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onApiCount(String machineIp, String serverId, String serviceId, ZRApiCountResult result) {
			// TODO Auto-generated method stub

		}
	};

	public void onRequest(String machineIp, String serverId, String serviceId, ZRRequestResult result);

	public void onTopology(String machineIp, String serverId, String serviceId, ZRTopologyResult result);

	public void onApiCount(String machineIp, String serverId, String serviceId, ZRApiCountResult result);

	public void onMachineStatus(ZRMachineStatus status);

	public void onServerStatus(ZRServerStatus status);

}
