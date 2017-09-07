package zr.monitor.bean.info;

public class ZRApiSettings {
	protected String methodName;
	protected boolean open;
	protected ZRAuthorityInfo[] authoritys;

 
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

	public ZRAuthorityInfo[] getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(ZRAuthorityInfo[] authoritys) {
		this.authoritys = authoritys;
	}

}
