package zr.monitor.cluster;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import v.common.helper.ParseUtil;
import v.common.helper.RandomHelper;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.bean.info.ZRApiVersionSettings;
import zr.monitor.bean.info.ZRServiceInfo;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.util.ZKER;
import zr.monitor.util.ZRMethodUtil;
import zr.monitor.util.ZRMonitorUtil;

final class InitializeTask implements Runnable {
	protected final ZRServerCluster cluster;

	public InitializeTask(ZRServerCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public void run() {
		final ZKER zker = cluster.zker;
		final ZRInfoMgr infoMgr = cluster.infoMgr;
		initSwitch(zker, infoMgr);
		initParams(zker);
		initSettings(zker, infoMgr);
		uploadServiceInfo(zker, infoMgr);
		lockHanlder();
	}

	private void initParams(ZKER zker) {
		Map<String, String> params = zker.getChildren(ZRCluster.ZR_PARAM);
		ZRParamUtil0.setParams0(params);
	}

	private void initSwitch(ZKER zker, ZRInfoMgr infoMgr) {
		String value = zker.get(ZRCluster.ZR_MACHINE_SWITCH);// 服务器设备开关
		infoMgr.setMachineOpen(ParseUtil.parseBoolean(value, Boolean.TRUE));
		value = zker.get(ZRCluster.ZR_SERVER_SWITCH);// 服务器开关
		infoMgr.setServerOpen(ParseUtil.parseBoolean(value, Boolean.TRUE));
		value = zker.get(ZRCluster.ZR_SERVICE_SWITCH);// 服务开关
		infoMgr.setServiceOpen(ParseUtil.parseBoolean(value, Boolean.TRUE));
	}

	private void initSettings(ZKER zker, ZRInfoMgr infoMgr) {
		Map<String, String> apiMap = zker.getChildren(ZRCluster.ZR_API_SETTINGS);
		Map<String, ZRApiSettings> map = new HashMap<>();
		for (String s : apiMap.values()) {
			ZRApiSettings settings = ZRMonitorUtil.jsonToObj(s, ZRApiSettings.class);
			if (settings != null)
				map.put(settings.getPackageName(), settings);
		}
		infoMgr.putApiSettings(map);

		Map<String, String> versionMap = zker.getChildren(ZRCluster.ZR_API_VERSION_SETTINGS);
		for (String s : versionMap.values()) {
			ZRApiVersionSettings settings = ZRMonitorUtil.jsonToObj(s, ZRApiVersionSettings.class);
			if (settings != null)
				infoMgr.putApiVersionSettings(settings);
		}
	}

	private void uploadServiceInfo(ZKER zker, ZRInfoMgr infoMgr) {
		ZRServiceInfo serviceInfo = infoMgr.getServiceInfo();
		zker.setTemp(ZRCluster.ZR_SERVICE_INFO, infoMgr.getServiceId(), ZRMonitorUtil.objToJson(serviceInfo));
		for (Enumeration<ZRApiInfo> it = infoMgr.apiInfoEnum(); it.hasMoreElements();) {
			ZRApiInfo info = it.nextElement();
			String key = ZRMethodUtil.getMethodFullName(info.getMethodName(), info.getVersion());
			zker.set(ZRCluster.ZR_API_VERSION_INFO, key, ZRMonitorUtil.objToJson(info));
		}
	}

	private void lockHanlder() {
		int delay = RandomHelper.randomInt(1000);
		cluster.loop.schedule(new LockMachineHandlerTask(cluster), delay);
		cluster.loop.schedule(new LockServerHandlerTask(cluster), delay);
	}

}
