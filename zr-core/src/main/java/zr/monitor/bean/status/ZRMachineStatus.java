package zr.monitor.bean.status;

import java.util.List;

import zr.monitor.bean.info.ZRDiskInfo;

public class ZRMachineStatus {
	protected String machineIp;
	protected double cpuLoad;
	protected long totolMemory;
	protected long usingMemory;
	protected long totalSwap;
	protected long usingSwap;
	protected List<ZRDiskInfo> disks;

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public double getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	public long getTotolMemory() {
		return totolMemory;
	}

	public void setTotolMemory(long totolMemory) {
		this.totolMemory = totolMemory;
	}

	public long getUsingMemory() {
		return usingMemory;
	}

	public void setUsingMemory(long usingMemory) {
		this.usingMemory = usingMemory;
	}

	public long getTotalSwap() {
		return totalSwap;
	}

	public void setTotalSwap(long totalSwap) {
		this.totalSwap = totalSwap;
	}

	public long getUsingSwap() {
		return usingSwap;
	}

	public void setUsingSwap(long usingSwap) {
		this.usingSwap = usingSwap;
	}

	public List<ZRDiskInfo> getDisks() {
		return disks;
	}

	public void setDisks(List<ZRDiskInfo> disks) {
		this.disks = disks;
	}
}
