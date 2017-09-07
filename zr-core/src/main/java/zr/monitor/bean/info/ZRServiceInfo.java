package zr.monitor.bean.info;

import java.util.List;

public class ZRServiceInfo {
	protected String machineIp;
	protected String serverId;
	protected String serviceId;
	protected List<ZRApiInfo> apis;

	public ZRServiceInfo(String machineIp, String serverId, String serviceId, List<ZRApiInfo> apis) {
		this.machineIp = machineIp;
		this.serverId = serverId;
		this.serviceId = serviceId;
		this.apis = apis;
	}

	public ZRServiceInfo() {
	}

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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<ZRApiInfo> getApis() {
		return apis;
	}

	public void setApis(List<ZRApiInfo> apis) {
		this.apis = apis;
	}

}
