package zr.monitor.bean.info;

import java.util.List;

import zr.monitor.util.ZRMonitorUtil;

public class ZRApiInfo implements Comparable<ZRApiInfo> {
	protected String module;
	protected String version;
	protected String methodName;
	protected String returnType;
	protected List<ZRParamInfo> params;
	protected String author;
	protected String date;
	protected String name;
	protected String description;
	protected List<String> defAuthoritys;
	protected List<String> uris;
	protected List<String> methods;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<ZRParamInfo> getParams() {
		return params;
	}

	public void setParams(List<ZRParamInfo> params) {
		this.params = params;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDefAuthoritys() {
		return defAuthoritys;
	}

	public void setDefAuthoritys(List<String> defAuthoritys) {
		this.defAuthoritys = defAuthoritys;
	}

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	@Override
	public int compareTo(ZRApiInfo o) {
		int i = methodName.compareTo(o.methodName);
		if (i != 0)
			return i;
		return ZRMonitorUtil.compareVersion(version, o.version);
	}

}
