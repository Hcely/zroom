package zr.monitor.bean.info;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;

import v.common.helper.ParseUtil;
import v.common.helper.StrUtil;
import zr.monitor.annotation.ZRParam;

public class ZRParamInfo {
	public static final ZRParamInfo parseStr(String str) {
		if (StrUtil.isEmpty(str))
			return null;
		List<String> strs = StrUtil.spiltAsList(str, '|', 6);
		if (strs.size() < 2)
			return null;
		ZRParamInfo hr = new ZRParamInfo();
		for (String e : strs) {
			e = e.trim();
			if (e.startsWith("type:"))
				hr.type = e.substring(5);
			else if (e.startsWith("name:"))
				hr.name = e.substring(5);
			else if (e.startsWith("org:"))
				hr.orgType = ParseUtil.parse(e.substring(4), Integer.class, ORG_NORMAL);
			else if (e.startsWith("req:"))
				hr.required = ParseUtil.parseBoolean(e.substring(4), Boolean.FALSE);
			else if (e.startsWith("desc:"))
				hr.description = e.substring(5);
			else if (e.startsWith("demo:"))
				hr.demo = e.substring(5);
		}
		if (StrUtil.isEmpty(hr.type) || StrUtil.isEmpty(hr.name))
			return null;
		return hr;
	}

	public static final ZRParamInfo parse(Parameter param) {
		Class<?> typeClazz = param.getType();
		ZRParam p = param.getAnnotation(ZRParam.class);
		return create(typeClazz, param.getName(), p);
	}

	public static final ZRParamInfo parse(Field field) {
		Class<?> typeClazz = field.getType();
		ZRParam p = field.getAnnotation(ZRParam.class);
		return create(typeClazz, field.getName(), p);
	}

	private static final ZRParamInfo create(Class<?> typeClazz, String name, ZRParam p) {
		ZRParamInfo hr = new ZRParamInfo();
		hr.type = getTypeSimpleName(typeClazz);
		hr.name = name;
		if (p == null) {
			hr.orgType = ORG_NORMAL;
			hr.required = !Object.class.isAssignableFrom(typeClazz);
		} else {
			hr.description = p.value();
			hr.demo = p.demo();
			hr.orgType = p.orgType();
			hr.required = p.required();
		}
		return hr;
	}

	public static final int ORG_NORMAL = 1;
	public static final int ORG_PATH = 2;
	public static final int ORG_HEADER = 4;
	public static final int ORG_COOKIE = 8;

	protected String type;
	protected String name;
	protected String description;
	protected String demo;
	protected int orgType;
	protected boolean required;

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

	public int getOrgType() {
		return orgType;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public static final String getTypeSimpleName(Class<?> clazz) {
		if (clazz == Boolean.class)
			return "boolean";
		if (clazz == Byte.class)
			return "byte";
		if (clazz == Short.class)
			return "short";
		if (clazz == Character.class)
			return "char";
		if (clazz == Integer.class)
			return "int";
		if (clazz == Long.class)
			return "long";
		if (clazz == Float.class)
			return "float";
		if (clazz == Double.class)
			return "double";
		return clazz.getSimpleName();
	}
}
