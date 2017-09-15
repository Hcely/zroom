package zr.monitor.info;

public class ZRMethodVersionSettings extends ZRMethodSettings {
	ZRMethodVersionSettings() {
		super(null, null);
	}

	protected volatile int topology;

	void set(boolean open, ZRDomainAuthority[] authoritys, int topology) {
		super.set(open, authoritys);
		this.topology = topology;
	}

	public int getTopology() {
		return topology;
	}

}
