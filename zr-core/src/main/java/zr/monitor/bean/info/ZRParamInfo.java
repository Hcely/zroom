package zr.monitor.bean.info;

public final class ZRParamInfo {

	protected String type;
	protected String name;
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

	public String getDescription() {
		return description;
	}

	public String getDemo() {
		return demo;
	}

}
