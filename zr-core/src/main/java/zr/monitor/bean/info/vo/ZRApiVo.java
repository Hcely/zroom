package zr.monitor.bean.info.vo;

import java.util.LinkedList;
import java.util.List;

public class ZRApiVo implements Comparable<ZRApiVo> {
	protected String module;
	protected String methodName;
	protected boolean open;
	protected List<String> authoritys;
	protected List<ZRApiInfoVo> versions;

	public ZRApiVo() {
	}

	public ZRApiVo(String module, String methodName) {
		this.module = module;
		this.methodName = methodName;
		this.open = true;
		this.versions = new LinkedList<>();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public List<String> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<String> authoritys) {
		this.authoritys = authoritys;
	}

	public List<ZRApiInfoVo> getVersions() {
		return versions;
	}

	public void setVersions(List<ZRApiInfoVo> versions) {
		this.versions = versions;
	}

	@Override
	public int compareTo(ZRApiVo o) {
		return module.compareTo(o.module);
	}

}
