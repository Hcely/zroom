package zr.monitor.cluster;

import java.util.Map;

import zr.common.util.JsonUtil;
import zr.common.zk.ZKER;
import zr.monitor.bean.info.ZRApiVersionSettings;
import zr.monitor.info.ZRInfoMgr;

public class GetApiVersionSettingsTask implements Runnable {
	protected final ZRServerCluster cluster;

	public GetApiVersionSettingsTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		ZKER zker = cluster.zker;
		ZRInfoMgr infoMgr = cluster.infoMgr;
		Map<String, String> versionMap = zker.getChildren(ZRCluster.ZR_API_VERSION_SETTINGS);
		for (String s : versionMap.values()) {
			ZRApiVersionSettings settings = JsonUtil.jsonToObj(s, ZRApiVersionSettings.class);
			if (settings != null)
				infoMgr.putApiVersionSettings(settings);
		}
	}

}
