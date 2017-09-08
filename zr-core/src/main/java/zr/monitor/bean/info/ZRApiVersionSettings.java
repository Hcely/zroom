package zr.monitor.bean.info;

public class ZRApiVersionSettings extends ZRApiSettings {
	protected String version;
	protected int topology;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getTopology() {
		return topology;
	}

	public void setTopology(int topology) {
		this.topology = topology;
	}
}
