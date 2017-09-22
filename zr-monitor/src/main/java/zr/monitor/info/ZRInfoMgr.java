package zr.monitor.info;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import v.Clearable;
import v.common.unit.DefEnumeration;
import v.server.unit.SysStatusInfo;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.bean.info.ZRApiVersionSettings;
import zr.monitor.bean.info.ZRDiskInfo;
import zr.monitor.bean.info.ZRMachineInfo;
import zr.monitor.bean.info.ZRServerInfo;
import zr.monitor.bean.info.ZRServiceInfo;
import zr.monitor.util.ZRMethodUtil;
import zr.monitor.util.ZRMonitorUtil;

public class ZRInfoMgr implements Clearable {
	protected final ZRApiInfoBuilder builder;
	protected final String machineIp;
	protected final String serverId;
	protected final String serviceId;
	protected final ZRMachineInfo machineInfo;
	protected final ZRServerInfo serverInfo;
	protected final ZRServiceInfo serviceInfo;
	protected final Map<String, ZRMethodSettings> apiSettingsMap;
	protected final Map<String, ZRMethodVersionSettings> apiVersionSettingsMap;
	protected final Map<String, ZRApiInfo> apiInfoMap;
	protected final List<ZRApiInfo> apiInfos;
	protected final List<String> apiNames;

	protected volatile boolean machineOpen;
	protected volatile boolean serverOpen;
	protected volatile boolean serviceOpen;

	protected volatile boolean serverHandler;
	protected volatile boolean machineHandler;

	public ZRInfoMgr(ZRApiInfoBuilder builder) {
		this.builder = builder == null ? new ZRDefApiInfoBuilder() : builder;
		this.machineIp = ZRMonitorUtil.getMachineIp();
		this.serverId = ZRMonitorUtil.getServerId();
		this.serviceId = ZRMonitorUtil.getServiceId();

		this.machineInfo = getMachineInfo(machineIp);
		this.serverInfo = getServerInfo(machineIp, serverId);

		this.apiSettingsMap = new HashMap<>();
		this.apiVersionSettingsMap = new HashMap<>();
		this.apiInfoMap = new HashMap<>();
		this.apiInfos = new LinkedList<>();
		this.apiNames = new LinkedList<>();
		this.serviceInfo = new ZRServiceInfo(machineIp, serverId, serviceId, apiNames);

		this.machineOpen = true;
		this.serverOpen = true;
		this.serviceOpen = true;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public String getServerId() {
		return serverId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public ZRMachineInfo getMachineInfo() {
		return machineInfo;
	}

	public ZRServerInfo getServerInfo() {
		return serverInfo;
	}

	public ZRServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	@Override
	public void clear() {
		apiSettingsMap.clear();
		apiVersionSettingsMap.clear();
		apiInfoMap.clear();
		apiInfos.clear();
		apiNames.clear();
	}

	public ZRApiInfo addGetApiInfo(Method method) {
		String methodName = ZRMethodUtil.getMethodName(method);
		String version = ZRMethodUtil.getMethodVersion(method);
		String fullName = ZRMethodUtil.getMethodFullName(methodName, version);
		ZRApiInfo info = apiInfoMap.get(fullName);
		if (info == null)
			synchronized (apiInfoMap) {
				if ((info = apiInfoMap.get(method)) == null) {
					String module = ZRMethodUtil.getMethodModule(method);
					info = builder.buildApiInfo(module, methodName, version, method);
					apiInfoMap.put(fullName, info);
					apiInfos.add(info);
					apiNames.add(fullName);
				}
			}
		return info;
	}

	public ZRMethodSettings getApiSettings(String module, String methodName) {
		ZRMethodSettings a = apiSettingsMap.get(methodName);
		if (a == null)
			synchronized (apiSettingsMap) {
				if ((a = apiSettingsMap.get(methodName)) == null)
					apiSettingsMap.put(methodName, a = new ZRMethodSettings(module, methodName));
			}
		return a;
	}

	public void putApiSettings(Map<String, ZRApiSettings> settingsMap) {
		for (ZRMethodSettings e : apiSettingsMap.values()) {
			ZRApiSettings0 s = new ZRApiSettings0();
			s.set(settingsMap.get(e.module));
			s.set(settingsMap.get(e.methodName));
			e.set(s.open, s.toAuthoritys());
		}
	}

	public ZRMethodVersionSettings getApiVersionSettings(String methodName, String version) {
		String key = ZRMethodUtil.getMethodFullName(methodName, version);
		ZRMethodVersionSettings sw = apiVersionSettingsMap.get(key);
		if (sw == null)
			synchronized (apiVersionSettingsMap) {
				if ((sw = apiVersionSettingsMap.get(key)) == null)
					apiVersionSettingsMap.put(key, sw = new ZRMethodVersionSettings());
			}
		return sw;
	}

	public void putApiVersionSettings(ZRApiVersionSettings settings) {
		getApiVersionSettings(settings.getMethodName(), settings.getVersion()).set(settings.isOpen(),
				ZRDomainAuthority.parseList(settings.getAuthoritys()), settings.getTopology());
	}

	public Enumeration<ZRApiInfo> apiInfoEnum() {
		return new DefEnumeration<>(apiInfos.iterator());
	}

	public SysStatusInfo getStatus() {
		return SysStatusInfo.getStatus();
	}

	public Map<String, ZRMethodSettings> getAuthorityMap() {
		return apiSettingsMap;
	}

	public boolean isMachineOpen() {
		return machineOpen;
	}

	public void setMachineOpen(boolean machineOpen) {
		this.machineOpen = machineOpen;
	}

	public void setServerOpen(boolean serverOpen) {
		this.serverOpen = serverOpen;
	}

	public void setServiceOpen(boolean serviceOpen) {
		this.serviceOpen = serviceOpen;
	}

	public boolean isServerOpen() {
		return serverOpen;
	}

	public boolean isServiceOpen() {
		return serviceOpen;
	}

	public boolean isOpen() {
		return serverOpen && machineOpen && serviceOpen;
	}

	public boolean isServerHandler() {
		return serverHandler;
	}

	public void setServerHandler(boolean serverHandler) {
		this.serverHandler = serverHandler;
	}

	public boolean isMachineHandler() {
		return machineHandler;
	}

	public void setMachineHandler(boolean machineHandler) {
		this.machineHandler = machineHandler;
	}

	private static final ZRMachineInfo getMachineInfo(String machineIp) {
		ZRMachineInfo info = new ZRMachineInfo();
		info.setMachineIp(machineIp);
		try {
			SystemInfo si = new SystemInfo();
			HardwareAbstractionLayer hw = si.getHardware();
			CentralProcessor cpu = hw.getProcessor();
			info.setCpuName(cpu.getName());
			info.setPhysicalProcessNum(cpu.getPhysicalProcessorCount());
			info.setLogicalProcessNum(cpu.getLogicalProcessorCount());
			info.setOsName(si.getOperatingSystem().toString());
			List<ZRDiskInfo> disks = new LinkedList<>();
			for (HWDiskStore e : hw.getDiskStores())
				disks.add(new ZRDiskInfo(e.getName(), e.getModel(), e.getSize(), 0));
			info.setDisks(disks);
		} finally {
		}
		SysStatusInfo sysInfo = SysStatusInfo.getStatus();
		info.setOsArch(sysInfo.getOsArch());
		info.setPhysicalMemory(sysInfo.getTotalPhysicalMemory());
		return info;
	}

	private static final ZRServerInfo getServerInfo(String machineIp, String serverId) {
		ZRServerInfo info = new ZRServerInfo();
		SysStatusInfo sysInfo = SysStatusInfo.getStatus();
		info.setMachineIp(machineIp);
		info.setServerId(serverId);
		info.setJvmProcessNum(sysInfo.getProcessNum());
		info.setMaxJvmMemory(sysInfo.getMaxJvmMemory());
		info.setJvmVersion(sysInfo.getJvmVersion());
		info.setJvmName(sysInfo.getJvmName());
		info.setBin(ZRMonitorUtil.getBinFolder());
		List<String> args = new ArrayList<>(ManagementFactory.getRuntimeMXBean().getInputArguments());
		info.setArgs(args);
		return info;
	}

}
