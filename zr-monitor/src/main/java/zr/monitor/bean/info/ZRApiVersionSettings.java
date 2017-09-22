package zr.monitor.bean.info;

import java.util.List;

public class ZRApiVersionSettings {
	protected String version;
	protected String methodName;
	protected boolean open;
	protected int topology;
	protected List<String> authoritys;

	public ZRApiVersionSettings() {
	}

	public ZRApiVersionSettings(String version, String methodName, boolean open, int topology,
			List<String> authoritys) {
		this.version = version;
		this.methodName = methodName;
		this.open = open;
		this.topology = topology;
		this.authoritys = authoritys;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getTopology() {
		return topology;
	}

	public void setTopology(int topology) {
		this.topology = topology;
	}

	public List<String> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<String> authoritys) {
		this.authoritys = authoritys;
	}

}
