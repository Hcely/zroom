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

	public ZRStatusTimerTask(ZRStatisticCenter center) {
		this.center = center;
		this.pMemorys = ManagementFactory.getMemoryPoolMXBeans();
		this.memorySize = pMemorys.size();
	}

	@Override
	public void run() {
		boolean serverHandler = center.infoMgr.isServerHandler();
		boolean machineHandler = center.infoMgr.isMachineHandler();
		if (!serverHandler && !machineHandler)
			return;
		SysStatusInfo sysInfo = SysStatusInfo.getStatus();
		if (serverHandler)
			center.handler.onServerStatus(getServerStatus(sysInfo));
		if (machineHandler)
			center.handler.onMachineStatus(getMachineStatus(sysInfo));
	}

	private final ZRServerStatus getServerStatus(SysStatusInfo sysInfo) {
		ZRServerStatus status = new ZRServerStatus();
		status.setMachineIp(center.infoMgr.getMachineIp());
		status.setServerId(center.infoMgr.getServerId());
		status.setMaxJvmMemory(sysInfo.getMaxJvmMemory());
		status.setTotalJvmMemory(sysInfo.getTotalJvmMemory());
		status.setUsingJvmMemory(sysInfo.getUsingJvmMemory());
		status.setThreadCount(sysInfo.getThreadCount());
		List<ZRJvmMemoryStatus> list = new ArrayList<>(memorySize);
		for (int i = 0; i < memorySize; ++i) {
			MemoryPoolMXBean e = pMemorys.get(i);
			MemoryUsage usage = e.getUsage();
			list.add(new ZRJvmMemoryStatus(e.getName(), e.getType().toString(), usage.getInit(), usage.getUsed(),
					usage.getCommitted(), usage.getMax()));
		}
		status.setMemorys(list);
		return status;
	}

	private final ZRMachineStatus getMachineStatus(SysStatusInfo sysInfo) {
		ZRMachineStatus status = new ZRMachineStatus();
		status.setMachineIp(center.infoMgr.getMachineIp());
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
