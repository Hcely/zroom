package zr.monitor.cluster;

import zr.monitor.util.ZRMonitorUtil;

public class LockServerHandlerTask implements Runnable {
	protected final ZRClusterServer cluster;

	public LockServerHandlerTask(ZRClusterServer cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		String key = ZRClusterServer.ZR_SERVER_INFO + cluster.infoMgr.getServerId();
		boolean b = cluster.zker.setLock(key, ZRMonitorUtil.objToJson(cluster.infoMgr.getServerInfo()));
		cluster.infoMgr.setServerHandler(b);
	}

}
