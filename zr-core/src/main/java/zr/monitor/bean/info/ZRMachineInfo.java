package zr.monitor.bean.info;

import java.util.List;

public class ZRMachineInfo {
	protected String machineIp;
	protected String cpuName;
	protected int physicalProcessNum;
	protected int logicalProcessNum;
	protected String osName;
	protected String osArch;
	protected long physicalMemory;
	protected List<ZRDiskInfo> disks;

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public String getCpuName() {
		return cpuName;
	}

	public void setCpuName(String cpuName) {
		this.cpuName = cpuName;
	}

	public int getPhysicalProcessNum() {
		return physicalProcessNum;
	}

	public void setPhysicalProcessNum(int physicalProcessNum) {
		this.physicalProcessNum = physicalProcessNum;
	}

	public int getLogicalProcessNum() {
		return logicalProcessNum;
	}

	public void setLogicalProcessNum(int logicalProcessNum) {
		this.logicalProcessNum = logicalProcessNum;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsArch() {
		return osArch;
	}

	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}

	public long getPhysicalMemory() {
		return physicalMemory;
	}

	public void setPhysicalMemory(long physicalMemory) {
		this.physicalMemory = physicalMemory;
	}

	public List<ZRDiskInfo> getDisks() {
		return disks;
	}

	public void setDisks(List<ZRDiskInfo> disks) {
		this.disks = disks;
	}

}
