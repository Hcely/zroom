package zr.monitor.cluster;

import zr.monitor.util.ZRMonitorUtil;

public class LockMachineHandlerTask implements Runnable {
	protected final ZRServerCluster cluster;

	public LockMachineHandlerTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		String key = ZRServerCluster.ZR_MACHINE_INFO + cluster.infoMgr.getMachineIp();
		boolean b = cluster.zker.setLock(key, ZRMonitorUtil.objToJson(cluster.infoMgr.getMachineInfo()));
		cluster.infoMgr.setMachineHandler(b);
	}

}
