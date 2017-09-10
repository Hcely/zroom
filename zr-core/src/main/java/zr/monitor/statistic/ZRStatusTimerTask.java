package zr.monitor.statistic;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import v.server.unit.SysStatusInfo;
import zr.monitor.bean.info.ZRDiskInfo;
import zr.monitor.bean.status.ZRJvmMemoryStatus;
import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

class ZRStatusTimerTask implements Runnable {
	protected final ZRStatisticCenter center;
	protected final List<MemoryPoolMXBean> pMemorys;
	protected final int memorySize;
	protected final ZRServerStatus serverStatus;
	protected final ZRMachineStatus machineStatus;

	public ZRStatusTimerTask(ZRStatisticCenter center) {
		this.center = center;
		this.pMemorys = new ArrayList<>(ManagementFactory.getMemoryPoolMXBeans());
		this.memorySize = pMemorys.size();
		this.serverStatus = new ZRServerStatus();

		List<ZRJvmMemoryStatus> jvmmemorys = new ArrayList<>(memorySize);
		for (int i = 0; i < memorySize; ++i) {
			MemoryPoolMXBean e = pMemorys.get(i);
			ZRJvmMemoryStatus m = new ZRJvmMemoryStatus();
			m.setName(e.getName());
			m.setType(e.getType().toString());
			jvmmemorys.add(m);
		}
		serverStatus.setMachineIp(center.infoMgr.getMachineIp());
		serverStatus.setServerId(center.infoMgr.getServerId());
		serverStatus.setMemorys(jvmmemorys);

		this.machineStatus = new ZRMachineStatus();
		machineStatus.setMachineIp(center.infoMgr.getMachineIp());
	}

	@Override
	public void run() {
		boolean serverHandler = center.infoMgr.isServerHandler();
		boolean machineHandler = center.infoMgr.isMachineHandler();
		if (!serverHandler && !machineHandler)
			return;
		SysStatusInfo sysInfo = SysStatusInfo.getStatus();
		if (serverHandler)
			center.handler.onServerStatus(updateServerStatus(sysInfo));
		if (machineHandler)
			center.handler.onMachineStatus(updateMachineStatus(sysInfo));
	}

	private final ZRServerStatus updateServerStatus(SysStatusInfo sysInfo) {
		ZRServerStatus status = this.serverStatus;
		status.setMaxJvmMemory(sysInfo.getMaxJvmMemory());
		status.setTotalJvmMemory(sysInfo.getTotalJvmMemory());
		status.setUsingJvmMemory(sysInfo.getUsingJvmMemory());
		status.setThreadCount(sysInfo.getThreadCount());
		List<ZRJvmMemoryStatus> jvmmemorys = status.getMemorys();
		for (int i = 0; i < memorySize; ++i) {
			MemoryUsage usage = pMemorys.get(i).getUsage();
			jvmmemorys.get(i).set(usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax());
		}
		return status;
	}

	private final ZRMachineStatus updateMachineStatus(SysStatusInfo sysInfo) {
		ZRMachineStatus status = this.machineStatus;
		status.setCpuLoad(sysInfo.getSysCpuLoad());
		status.setTotolMemory(sysInfo.getTotalPhysicalMemory());
		status.setUsingMemory(sysInfo.getUsingPhysicalMemory());
		status.setTotalSwap(sysInfo.getTotalSwap());
		status.setUsingSwap(sysInfo.getUsingSwap());
		SystemInfo si = new SystemInfo();
		HWDiskStore[] disks = si.getHardware().getDiskStores();
		List<ZRDiskInfo> list = new ArrayList<>(disks.length);
		for (HWDiskStore e : disks)
			list.add(new ZRDiskInfo(e.getName(), e.getModel(), e.getSize(), e.getReadBytes() + e.getWriteBytes()));
		status.setDisks(list);
		return status;
	}
}
