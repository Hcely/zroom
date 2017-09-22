package zr.monitor.info;

public class ZRMethodSettings {
	protected final String module;
	protected final String methodName;

	protected volatile boolean open;
	protected volatile ZRDomainAuthority[] authoritys;

	ZRMethodSettings(String module, String methodName) {
		this.module = module;
		this.methodName = methodName;
	}

	void set(boolean open, ZRDomainAuthority[] authoritys) {
		this.open = open;
		this.authoritys = authoritys;
	}

	public ZRDomainAuthority[] getAuthoritys() {
		return authoritys;
	}

	public boolean isOpen() {
		return open;
	}

}
