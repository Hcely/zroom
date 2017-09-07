package zr.monitor.status;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import v.Initializable;
import v.VObject;
import v.common.unit.VThreadLoop;
import v.common.unit.VThreadLoop.VTimerTask;
import v.server.unit.SysStatusInfo;
import zr.monitor.bean.info.ZRDiskInfo;
import zr.monitor.bean.status.ZRJvmMemoryStatus;
import zr.monitor.bean.status.ZRMachineStatus;
import zr.monitor.bean.status.ZRServerStatus;

public class ZRMonitorStatusMgr implements VObject, Initializable {

	protected final String machineIp;
	protected final String serverId;
	protected final VThreadLoop loop;
	protected final ZRStatusHandler handler;
	protected final List<MemoryPoolMXBean> pMemorys;
	protected final int memorySize;

	protected volatile boolean serverStatus;
	protected volatile boolean machineStatus;

	protected VTimerTask task;
	protected boolean init;
	protected boolean destory;

	public ZRMonitorStatusMgr(String machineIp, String serverId, VThreadLoop loop, ZRStatusHandler handler) {
		this.machineIp = machineIp;
		this.serverId = serverId;
		this.loop = loop;
		this.handler = handler;
		this.pMemorys = ManagementFactory.getMemoryPoolMXBeans();
		this.memorySize = pMemorys.size();

		this.serverStatus = false;
		this.machineStatus = false;

	}

	@Override
	public void init() {
		if (init)
			return;
		synchronized (this) {
			if (init)
				return;
			init = true;
			task = loop.schedule(new Task(), 10000, 60000);
		}
	}

	@Override
	public void destory() {
		if (destory)
			return;
		synchronized (this) {
			if (destory || !init)
				return;
			destory = true;
		}
		if (task != null)
			task.cancel();
		task = null;
	}

	public boolean isServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}

	public boolean isMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(boolean machineStatus) {
		this.machineStatus = machineStatus;
	}

	private class Task implements Runnable {

		@Override
		public void run() {
			if (handler == null)
				return;
			if (!serverStatus && !serverStatus)
				return;
			SysStatusInfo sysInfo = SysStatusInfo.getStatus();
			if (serverStatus)
				handler.onServerStatus(getServerStatus(sysInfo));
			if (serverStatus)
				handler.onMachineStatus(getMachineStatus(sysInfo));
		}
	}

	@Override
	public boolean isInit() {
		return init;
	}

	@Override
	public boolean isDestory() {
		return destory;
	}

	private final ZRServerStatus getServerStatus(SysStatusInfo sysInfo) {
		ZRServerStatus status = new ZRServerStatus();
		status.setMachineIp(machineIp);
		status.setServerId(serverId);
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
		status.setMachineIp(machineIp);
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
