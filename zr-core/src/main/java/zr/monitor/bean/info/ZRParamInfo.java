package zr.monitor.bean.info;

public final class ZRParamInfo {
	public static final int NORMAL_PARAM = 1;
	public static final int PATH_PARAM = 2;
	public static final int HEADER_PARAM = 3;
	public static final int COOKIE_PARAM = 4;

	protected String type;
	protected String name;
	protected int paramType;
	protected boolean required;
	protected String description;
	protected String demo;

	public ZRParamInfo() {
	}

	void setType(String type) {
		this.type = type;
	}

	void setName(String name) {
		this.name = name;
	}

	void setParamType(int paramType) {
		this.paramType = paramType;
	}

	void setRequired(boolean required) {
		this.required = required;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDemo(String demo) {
		this.demo = demo;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getParamType() {
		return paramType;
	}

	public boolean isRequired() {
		return required;
	}

	public String getDescription() {
		return description;
	}

	public String getDemo() {
		return demo;
	}

}
