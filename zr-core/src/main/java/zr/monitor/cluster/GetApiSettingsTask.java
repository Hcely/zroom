package zr.monitor.cluster;

import java.util.Map;

import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.util.ZKER;
import zr.monitor.util.ZRMonitorUtil;

public class GetApiSettingsTask implements Runnable {
	protected final ZRClusterServer cluster;

	public GetApiSettingsTask(ZRClusterServer cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		ZKER zker = cluster.zker;
		ZRInfoMgr infoMgr = cluster.infoMgr;
		Map<String, String> apiMap = zker.getChildren(ZRClusterServer.ZR_API_SETTINGS);
		for (String s : apiMap.values()) {
			ZRApiSettings settings = ZRMonitorUtil.jsonToObj(s, ZRApiSettings.class);
			if (settings != null)
				infoMgr.putApiSettings(settings);
		}
	}

}
