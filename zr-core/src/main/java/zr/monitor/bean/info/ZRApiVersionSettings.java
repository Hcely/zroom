package zr.monitor.bean.info;

public class ZRApiVersionSettings extends ZRApiSettings {
	protected String version;
	protected boolean topology;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isTopology() {
		return topology;
	}

	public void setTopology(boolean topology) {
		this.topology = topology;
	}

}
