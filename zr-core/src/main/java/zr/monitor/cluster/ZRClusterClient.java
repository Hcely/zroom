package zr.monitor.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import v.common.helper.ParseUtil;
import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.bean.info.ZRApiVersionSettings;
import zr.monitor.bean.info.ZRServiceInfo;
import zr.monitor.bean.info.vo.ZRApiInfoVo;
import zr.monitor.bean.info.vo.ZRApiVo;
import zr.monitor.bean.info.vo.ZRMachineInfoVo;
import zr.monitor.bean.info.vo.ZRModuleVo;
import zr.monitor.bean.info.vo.ZRServerInfoVo;
import zr.monitor.bean.info.vo.ZRServiceInfoVo;
import zr.monitor.util.ZRMethodUtil;
import zr.monitor.util.ZRMonitorUtil;

public class ZRClusterClient extends ZRCluster {

	public void setApiSettings(String packageName, boolean open, List<String> authoritys) {
		ZRApiSettings e = new ZRApiSettings(packageName, open, authoritys);
		zker.set(ZR_API_SETTINGS, packageName, ZRMonitorUtil.objToJson(e));
	}

	public void setApiVersionSettings(String methodName, String version, boolean open, int topology,
			List<String> authoritys) {
		ZRApiVersionSettings e = new ZRApiVersionSettings(version, methodName, open, topology, authoritys);
		String fullName = ZRMethodUtil.getMethodFullName(methodName, version);
		zker.set(ZR_API_VERSION_SETTINGS, fullName, ZRMonitorUtil.objToJson(e));
	}

	public void openOrCloseMachine(String machineIp, boolean open) {
		zker.set(ZR_MACHINE_SWITCH, machineIp, String.valueOf(open));
	}

	public void openOrCloseServer(String serverId, boolean open) {
		zker.set(ZR_SERVER_SWITCH, serverId, String.valueOf(open));
	}

	public void openOrCloseService(String serviceId, boolean open) {
		zker.set(ZR_SERVICE_SWITCH, serviceId, String.valueOf(open));
	}

	public ZRMachineInfoVo getMachineInfo(String ip) {
		String value = zker.get(ZR_MACHINE_INFO, ip);
		if (value == null)
			return null;
		ZRMachineInfoVo info = ZRMonitorUtil.jsonToObj(value, ZRMachineInfoVo.class);
		boolean open = ParseUtil.parseBoolean(zker.get(ZR_MACHINE_SWITCH, ip), Boolean.TRUE);
		info.setOpen(open);
		boolean online = zker.get(ZR_MACHINE_HANDLER, ip) != null;
		info.setOnline(online);
		return info;
	}

	public List<ZRMachineInfoVo> getMachineInfo() {
		Map<String, String> values = zker.getChildren(ZR_MACHINE_INFO);
		Map<String, ZRMachineInfoVo> infoMap = new HashMap<>();
		for (String e : values.values()) {
			ZRMachineInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRMachineInfoVo.class);
			info.setOpen(true);
			infoMap.put(info.getMachineIp(), info);
		}
		values = zker.getChildren(ZR_MACHINE_SWITCH);
		for (Entry<String, String> e : values.entrySet()) {
			ZRMachineInfoVo info = infoMap.get(e.getKey());
			if (info != null)
				info.setOpen(ParseUtil.parseBoolean(e.getValue(), Boolean.TRUE));
		}
		values = zker.getChildren(ZR_MACHINE_HANDLER);
		for (Entry<String, String> e : values.entrySet()) {
			ZRMachineInfoVo info = infoMap.get(e.getKey());
			if (info != null)
				info.setOnline(true);
		}
		List<ZRMachineInfoVo> infos = new ArrayList<>(infoMap.values());
		Collections.sort(infos);
		return infos;
	}

	public ZRServerInfoVo getServerInfo(String serverId) {
		String value = zker.get(ZR_SERVER_INFO, serverId);
		if (value == null)
			return null;
		ZRServerInfoVo info = ZRMonitorUtil.jsonToObj(value, ZRServerInfoVo.class);
		boolean open = ParseUtil.parseBoolean(zker.get(ZR_SERVER_SWITCH, serverId), Boolean.TRUE);
		info.setOpen(open);
		return info;
	}

	public List<ZRServerInfoVo> getServerInfoByMachineIp(String ip) {
		Map<String, String> values = zker.getChildren(ZR_SERVER_INFO);
		Map<String, ZRServerInfoVo> infoMap = new HashMap<>();
		for (String e : values.values()) {
			ZRServerInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRServerInfoVo.class);
			if (ip.equals(info.getMachineIp())) {
				info.setOpen(true);
				infoMap.put(info.getServerId(), info);
			}
		}
		values = zker.gets(ZR_SERVER_SWITCH, infoMap.keySet());
		for (Entry<String, String> e : values.entrySet()) {
			ZRServerInfoVo info = infoMap.get(e.getKey());
			if (info != null)
				info.setOpen(ParseUtil.parseBoolean(e.getValue(), Boolean.TRUE));
		}
		List<ZRServerInfoVo> list = new ArrayList<>(infoMap.values());
		Collections.sort(list);
		return list;
	}

	public List<ZRServerInfoVo> getServerInfo() {
		Map<String, String> values = zker.getChildren(ZR_SERVER_INFO);
		Map<String, ZRServerInfoVo> infoMap = new HashMap<>();
		for (String e : values.values()) {
			ZRServerInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRServerInfoVo.class);
			info.setOpen(true);
			infoMap.put(info.getServerId(), info);
		}
		values = zker.getChildren(ZR_SERVER_SWITCH);
		for (Entry<String, String> e : values.entrySet()) {
			ZRServerInfoVo info = infoMap.get(e.getKey());
			if (info != null)
				info.setOpen(ParseUtil.parseBoolean(e.getValue(), Boolean.TRUE));
		}
		List<ZRServerInfoVo> list = new ArrayList<>(infoMap.values());
		Collections.sort(list);
		return list;
	}

	public ZRServiceInfoVo getServiceInfo(String serviceId) {
		String value = zker.get(ZR_SERVICE_INFO, serviceId);
		if (value == null)
			return null;
		ZRServiceInfoVo info = ZRMonitorUtil.jsonToObj(value, ZRServiceInfoVo.class);
		boolean open = ParseUtil.parseBoolean(zker.get(ZR_SERVICE_SWITCH, serviceId), Boolean.TRUE);
		info.setOpen(open);
		return info;
	}

	public List<ZRServiceInfoVo> getServiceInfoByServerId(String serverId) {
		Map<String, String> values = zker.getChildren(ZR_SERVICE_INFO);
		Map<String, ZRServiceInfoVo> infoMap = new HashMap<>();
		for (String e : values.values()) {
			ZRServiceInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRServiceInfoVo.class);
			if (serverId.equals(info.getServerId())) {
				info.setOpen(true);
				infoMap.put(info.getServerId(), info);
			}
		}
		values = zker.gets(ZR_SERVICE_SWITCH, infoMap.keySet());
		for (Entry<String, String> e : values.entrySet()) {
			ZRServiceInfoVo info = infoMap.get(e.getKey());
			if (info != null)
				info.setOpen(ParseUtil.parseBoolean(e.getValue(), Boolean.TRUE));
		}
		List<ZRServiceInfoVo> list = new ArrayList<>(infoMap.values());
		Collections.sort(list);
		return list;
	}

	public List<ZRModuleVo> getApis() {
		Map<String, String> values = zker.getChildren(ZR_API_VERSION_INFO);
		Map<String, ZRApiInfoVo> apiMap = new HashMap<>();
		for (String e : values.values()) {
			ZRApiInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRApiInfoVo.class);
			info.setOpen(true);
			String key = ZRMethodUtil.getMethodFullName(info.getMethodName(), info.getVersion());
			apiMap.put(key, info);
		}
		values = zker.getChildren(ZR_API_VERSION_SETTINGS);
		for (Entry<String, String> e : values.entrySet()) {
			ZRApiInfoVo info = apiMap.get(e.getKey());
			if (info != null) {
				ZRApiVersionSettings settings = ZRMonitorUtil.jsonToObj(e.getValue(), ZRApiVersionSettings.class);
				info.setOpen(settings.isOpen());
				info.setAuthoritys(settings.getAuthoritys());
				info.setTopology(settings.getTopology());
			}
		}
		return getModules(apiMap);
	}

	public List<ZRModuleVo> getApisByServiceId(String serviceId) {
		String value = zker.get(ZR_SERVICE_INFO, serviceId);
		if (value == null)
			return new LinkedList<>();
		ZRServiceInfo serviceinfo = ZRMonitorUtil.jsonToObj(value, ZRServiceInfo.class);
		List<String> apis = serviceinfo.getApis();
		if (apis == null || apis.isEmpty())
			return new LinkedList<>();
		Map<String, String> values = zker.gets(ZR_API_VERSION_INFO, apis);
		Map<String, ZRApiInfoVo> apiMap = new HashMap<>();
		for (String e : values.values()) {
			ZRApiInfoVo info = ZRMonitorUtil.jsonToObj(e, ZRApiInfoVo.class);
			info.setOpen(true);
			String key = ZRMethodUtil.getMethodFullName(info.getMethodName(), info.getVersion());
			apiMap.put(key, info);
		}
		values = zker.gets(ZR_API_VERSION_SETTINGS, apis);
		for (Entry<String, String> e : values.entrySet()) {
			ZRApiInfoVo info = apiMap.get(e.getKey());
			if (info != null) {
				ZRApiVersionSettings settings = ZRMonitorUtil.jsonToObj(e.getValue(), ZRApiVersionSettings.class);
				info.setOpen(settings.isOpen());
				info.setAuthoritys(settings.getAuthoritys());
				info.setTopology(settings.getTopology());
			}
		}
		if (apiMap.isEmpty())
			return new LinkedList<>();
		return getModules(apiMap);
	}

	private List<ZRModuleVo> getModules(Map<String, ZRApiInfoVo> apiMap) {
		Map<String, String> values = zker.getChildren(ZR_API_SETTINGS);
		Map<String, ZRApiSettings> apiSettings = new HashMap<>();
		for (String e : values.values()) {
			ZRApiSettings settings = ZRMonitorUtil.jsonToObj(e, ZRApiSettings.class);
			apiSettings.put(settings.getPackageName(), settings);
		}

		Map<String, List<ZRApiInfoVo>> moduleMap = new HashMap<>();
		List<ZRApiInfoVo> infos = new ArrayList<>(apiMap.values());
		Collections.sort(infos);
		for (ZRApiInfoVo e : infos) {
			List<ZRApiInfoVo> list = moduleMap.get(e.getModule());
			if (list == null)
				moduleMap.put(e.getModule(), list = new LinkedList<>());
			list.add(e);
		}
		List<ZRModuleVo> hr = new ArrayList<>(moduleMap.size());
		for (Entry<String, List<ZRApiInfoVo>> e : moduleMap.entrySet()) {
			Map<String, ZRApiVo> apiMap0 = new LinkedHashMap<>();
			String module = e.getKey();
			for (ZRApiInfoVo a : e.getValue()) {
				ZRApiVo api = apiMap0.get(a.getMethodName());
				if (api == null) {
					api = new ZRApiVo(module, a.getMethodName());
					ZRApiSettings settings = apiSettings.get(a.getMethodName());
					if (settings != null) {
						api.setAuthoritys(settings.getAuthoritys());
						api.setOpen(settings.isOpen());
					}
					apiMap0.put(module, api);
				}
				api.getVersions().add(a);
			}
			ZRModuleVo vo = new ZRModuleVo(module, new ArrayList<>(apiMap0.values()));
			ZRApiSettings settings = apiSettings.get(module);
			if (settings != null) {
				vo.setAuthoritys(settings.getAuthoritys());
				vo.setOpen(settings.isOpen());
			}
			hr.add(vo);
		}
		Collections.sort(hr);
		return hr;
	}

}
