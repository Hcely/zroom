package zr.monitor.bean.info;

public class ZRApiInfo {
	protected String version;
	protected String methodName;
	protected String returnType;
	protected ZRParamInfo[] params;
	protected String author;
	protected String date;
	protected String name;
	protected String description;
	protected ZRAuthorityInfo[] defAuthoritys;

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

	public ZRParamInfo[] getParams() {
		return params;
	}

	public void setParams(ZRParamInfo[] params) {
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

	public ZRAuthorityInfo[] getDefAuthoritys() {
		return defAuthoritys;
	}

	public void setDefAuthoritys(ZRAuthorityInfo[] defAuthoritys) {
		this.defAuthoritys = defAuthoritys;
	}

}
