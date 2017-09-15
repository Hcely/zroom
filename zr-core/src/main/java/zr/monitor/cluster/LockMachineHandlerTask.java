package zr.monitor.cluster;

import zr.monitor.info.ZRInfoMgr;
import zr.monitor.util.ZRMonitorUtil;

public class LockMachineHandlerTask implements Runnable {
	protected final ZRServerCluster cluster;

	public LockMachineHandlerTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		ZRInfoMgr infoMgr = cluster.infoMgr;
		String machineIp = infoMgr.getMachineIp();
		boolean b = cluster.zker.setLock(ZRCluster.ZR_MACHINE_HANDLER, machineIp, infoMgr.getServiceId());
		infoMgr.setMachineHandler(b);
		if (b)
			cluster.zker.set(ZRCluster.ZR_MACHINE_INFO, machineIp, ZRMonitorUtil.objToJson(infoMgr.getMachineInfo()));
	}

}
