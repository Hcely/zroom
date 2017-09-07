package zr.monitor.bean.info;

import java.util.List;

public class ZRServerInfo {
	protected String machineIp;
	protected String serverId;
	protected String jvmVersion;
	protected String jvmName;
	protected long maxJvmMemory;
	protected int jvmProcessNum;
	protected String bin;
	protected List<String> args;

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

	public String getJvmVersion() {
		return jvmVersion;
	}

	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}

	public String getJvmName() {
		return jvmName;
	}

	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}

	public long getMaxJvmMemory() {
		return maxJvmMemory;
	}

	public void setMaxJvmMemory(long maxJvmMemory) {
		this.maxJvmMemory = maxJvmMemory;
	}

	public int getJvmProcessNum() {
		return jvmProcessNum;
	}

	public void setJvmProcessNum(int jvmProcessNum) {
		this.jvmProcessNum = jvmProcessNum;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args = args;
	}

}
