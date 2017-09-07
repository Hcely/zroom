package zr.monitor.bean.status;

import java.util.List;

public class ZRServerStatus {
	protected String machineIp;
	protected String serverId;
	protected long maxJvmMemory;
	protected long totalJvmMemory;
	protected long usingJvmMemory;
	protected int threadCount;
	protected List<ZRJvmMemoryStatus> memorys;

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public long getMaxJvmMemory() {
		return maxJvmMemory;
	}

	public void setMaxJvmMemory(long maxJvmMemory) {
		this.maxJvmMemory = maxJvmMemory;
	}

	public long getTotalJvmMemory() {
		return totalJvmMemory;
	}

	public void setTotalJvmMemory(long totalJvmMemory) {
		this.totalJvmMemory = totalJvmMemory;
	}

	public long getUsingJvmMemory() {
		return usingJvmMemory;
	}

	public void setUsingJvmMemory(long usingJvmMemory) {
		this.usingJvmMemory = usingJvmMemory;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public List<ZRJvmMemoryStatus> getMemorys() {
		return memorys;
	}

	public void setMemorys(List<ZRJvmMemoryStatus> memorys) {
		this.memorys = memorys;
	}
}
