package zr.monitor.cluster;

import zr.common.util.JsonUtil;

public class LockServerHandlerTask implements Runnable {
	protected final ZRServerCluster cluster;

	public LockServerHandlerTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		String key = ZRServerCluster.ZR_SERVER_INFO + cluster.infoMgr.getServerId();
		boolean b = cluster.zker.setLock(key, JsonUtil.objToJson(cluster.infoMgr.getServerInfo()));
		cluster.infoMgr.setServerHandler(b);
	}

}
