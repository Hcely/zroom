package zr.monitor.cluster;

import java.util.Map;

public class GetParamsTask implements Runnable {
	protected final ZRClusterServer cluster;

	public GetParamsTask(ZRClusterServer cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		Map<String, String> params = cluster.zker.getChildren(ZRClusterServer.ZR_PARAM);
		ZRParamUtil0.setParams0(params);
	}
}
