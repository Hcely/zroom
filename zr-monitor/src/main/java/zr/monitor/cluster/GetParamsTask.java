package zr.monitor.cluster;

import java.util.Map;

import zr.monitor.ZRParamUtil;

public class GetParamsTask extends ZRParamUtil implements Runnable {
	protected final ZRServerCluster cluster;

	public GetParamsTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		Map<String, String> params = cluster.zker.getChildren(ZRCluster.ZR_PARAM);
		setParams0(params);
	}

	static final void setParams0(Map<String, String> params) {
		ZRParamUtil.setParams(params);
	}

}
