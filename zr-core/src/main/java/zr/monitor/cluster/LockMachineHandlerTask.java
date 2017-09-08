package zr.monitor.cluster;

import zr.monitor.util.ZRMonitorUtil;

public class LockMachineHandlerTask implements Runnable {
	protected final ZRClusterServer cluster;

	public LockMachineHandlerTask(ZRClusterServer cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		String key = ZRClusterServer.ZR_MACHINE_INFO + cluster.infoMgr.getMachineIp();
		boolean b = cluster.zker.setLock(key, ZRMonitorUtil.objToJson(cluster.infoMgr.getMachineInfo()));
		cluster.statisticCenter.setMachineStatus(b);
	}

}
