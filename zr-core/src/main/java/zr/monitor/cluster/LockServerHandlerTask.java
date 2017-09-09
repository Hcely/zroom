package zr.monitor.cluster;

import zr.monitor.util.ZRMonitorUtil;

public class LockServerHandlerTask implements Runnable {
	protected final ZRServerCluster cluster;

	public LockServerHandlerTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		String key = ZRServerCluster.ZR_SERVER_INFO + cluster.infoMgr.getServerId();
		boolean b = cluster.zker.setLock(key, ZRMonitorUtil.objToJson(cluster.infoMgr.getServerInfo()));
		cluster.infoMgr.setServerHandler(b);
	}

}
