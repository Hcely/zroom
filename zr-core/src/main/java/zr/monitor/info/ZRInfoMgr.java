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
import v.common.helper.StrUtil;
import v.common.unit.DefEnumeration;
import v.server.unit.SysStatusInfo;
import zr.monitor.annotation.ZRVersion;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRApiSettings;
import zr.monitor.bean.info.ZRApiVersionSettings;
import zr.monitor.bean.info.ZRDiskInfo;
import zr.monitor.bean.info.ZRMachineInfo;
import zr.monitor.bean.info.ZRServerInfo;
import zr.monitor.bean.info.ZRServiceInfo;
import zr.monitor.util.ZRMonitorUtil;

public class ZRInfoMgr {
	protected final ZRInfoCreator creater;
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

	protected volatile boolean topologyOpen;
	protected volatile boolean machineOpen;
	protected volatile boolean serverOpen;
	protected volatile boolean serviceOpen;

	public ZRInfoMgr(ZRInfoCreator creater) {
		this.creater = creater;
		this.machineIp = ZRMonitorUtil.getMachineIp();
		this.serverId = ZRMonitorUtil.getServerId();
		this.serviceId = ZRMonitorUtil.getServiceId();

		this.machineInfo = getMachineInfo(machineIp);
		this.serverInfo = getServerInfo(machineIp, serverId);

		this.apiSettingsMap = new HashMap<>();
		this.apiVersionSettingsMap = new HashMap<>();
		this.apiInfoMap = new HashMap<>();
		this.apiInfos = new LinkedList<>();

		this.serviceInfo = new ZRServiceInfo(machineIp, serverId, serviceId, apiInfos);

		this.topologyOpen = false;
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

	public ZRApiInfo addGetApiInfo(Method method) {
		String methodName = getMethodName(method);
		String version = getMethodVersion(method);
		String key = ZRMonitorUtil.getApiKey(methodName, version);
		ZRApiInfo info = apiInfoMap.get(key);
		if (info == null)
			synchronized (apiInfoMap) {
				if ((info = apiInfoMap.get(method)) == null) {
					info = createInfo(methodName, version, method);
					apiInfoMap.put(key, info);
					apiInfos.add(info);
				}
			}
		return info;
	}

	private ZRApiInfo createInfo(String methodName, String version, Method method) {
		if (creater != null)
			return creater.createInfo(methodName, version, method);
		return new ZRApiInfo(methodName, version, method);
	}

	public ZRMethodSettings getApiSettings(String methodName) {
		ZRMethodSettings a = apiSettingsMap.get(methodName);
		if (a == null)
			synchronized (apiSettingsMap) {
				if ((a = apiSettingsMap.get(methodName)) == null)
					apiSettingsMap.put(methodName, a = new ZRMethodSettings());
			}
		return a;
	}

	public void putApiSettings(ZRApiSettings settings) {
		getApiSettings(settings.getMethodName()).set(settings.isOpen(), settings.getAuthoritys());
	}

	public ZRMethodVersionSettings getApiVersionSettings(String methodName, String version) {
		String key = ZRMonitorUtil.getApiKey(methodName, version);
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
				settings.getAuthoritys(), settings.getTopology());
	}

	public Enumeration<ZRApiInfo> infos() {
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

	private static final String getMethodName(Method method) {
		String clazzName = method.getDeclaringClass().getName();
		String methodName = method.getName();
		StringBuilder sb = new StringBuilder(clazzName.length() + methodName.length() + 3);
		sb.append(clazzName).append('.').append(methodName).append("()");
		return StrUtil.sbToString(sb);
	}

	private static final String getMethodVersion(Method method) {
		ZRVersion version = method.getAnnotation(ZRVersion.class);
		return version == null ? "0.0.0" : version.value();
	}

	private static final ZRMachineInfo getMachineInfo(String machineIp) {
		ZRMachineInfo info = new ZRMachineInfo();
		info.setMachineIp(machineIp);
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hw = si.getHardware();
		CentralProcessor cpu = hw.getProcessor();
		info.setCpuName(cpu.getName());
		info.setPhysicalProcessNum(cpu.getPhysicalProcessorCount());
		info.setLogicalProcessNum(cpu.getLogicalProcessorCount());
		info.setOsName(si.getOperatingSystem().toString());
		SysStatusInfo sysInfo = SysStatusInfo.getStatus();
		info.setOsArch(sysInfo.getOsArch());
		info.setPhysicalMemory(sysInfo.getTotalPhysicalMemory());
		List<ZRDiskInfo> disks = new LinkedList<>();
		for (HWDiskStore e : hw.getDiskStores())
			disks.add(new ZRDiskInfo(e.getName(), e.getModel(), e.getSize(), 0));
		info.setDisks(disks);
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
