package zr.monitor.bean.info;

import java.util.List;

public class ZRServiceInfo implements Comparable<ZRServiceInfo> {
	protected String machineIp;
	protected String serverId;
	protected String serviceId;
	protected List<String> apis;

	public ZRServiceInfo(String machineIp, String serverId, String serviceId, List<String> apis) {
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

	public List<String> getApis() {
		return apis;
	}

	public void setApis(List<String> apis) {
		this.apis = apis;
	}

	@Override
	public int compareTo(ZRServiceInfo o) {
		int i = machineIp.compareTo(o.machineIp);
		if (i != 0)
			return i;
		i = serverId.compareTo(o.serverId);
		if (i != 0)
			return i;
		return serviceId.compareTo(serviceId);
	}

}
