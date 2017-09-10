package zr.monitor.bean.info;

import java.lang.reflect.Parameter;

import zr.monitor.annotation.ZRParam;

public final class ZRParamInfo {

	protected String type;
	protected String name;
	protected String description;
	protected String demo;

	public ZRParamInfo() {
	}

	public ZRParamInfo(Parameter param) {
		type = param.getType().getSimpleName();
		name = param.getName();
		ZRParam p = param.getAnnotation(ZRParam.class);
		description = p == null ? null : p.value();
		demo = p == null ? null : p.demo();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

}
