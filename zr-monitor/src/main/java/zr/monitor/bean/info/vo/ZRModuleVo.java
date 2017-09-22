package zr.monitor.bean.info.vo;

import java.util.List;

public class ZRModuleVo implements Comparable<ZRModuleVo> {
	protected String module;
	protected boolean open;
	protected List<String> authoritys;
	protected List<ZRApiVo> apis;

	public ZRModuleVo() {
	}

	public ZRModuleVo(String module, List<ZRApiVo> apis) {
		this.module = module;
		this.open = true;
		this.apis = apis;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public List<ZRApiVo> getApis() {
		return apis;
	}

	public void setApis(List<ZRApiVo> apis) {
		this.apis = apis;
	}

	@Override
	public int compareTo(ZRModuleVo o) {
		return module.compareTo(o.module);
	}

}
