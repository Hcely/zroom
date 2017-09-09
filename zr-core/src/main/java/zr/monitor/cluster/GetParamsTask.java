package zr.monitor.cluster;

import java.util.Map;

public class GetParamsTask implements Runnable {
	protected final ZRServerCluster cluster;

	public GetParamsTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		Map<String, String> params = cluster.zker.getChildren(ZRServerCluster.ZR_PARAM);
		ZRParamUtil0.setParams0(params);
	}
}
