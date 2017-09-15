package zr.monitor.cluster;

import java.util.HashMap;
import java.util.Map;

import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.util.ZRMonitorUtil;

public class GetApiSettingsTask implements Runnable {
	protected final ZRServerCluster cluster;

	public GetApiSettingsTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		Map<String, String> apiMap = cluster.zker.getChildren(ZRCluster.ZR_API_SETTINGS);
		Map<String, ZRApiSettings> map = new HashMap<>();
		for (String s : apiMap.values()) {
			ZRApiSettings settings = ZRMonitorUtil.jsonToObj(s, ZRApiSettings.class);
			if (settings != null)
				map.put(settings.getPackageName(), settings);
		}
		cluster.infoMgr.putApiSettings(map);
	}

}
