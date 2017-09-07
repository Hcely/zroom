package zr.monitor.bean.info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import zr.monitor.annotation.ZRAuthor;
import zr.monitor.annotation.ZRAuthority;
import zr.monitor.annotation.ZRDate;
import zr.monitor.annotation.ZRDescription;
import zr.monitor.annotation.ZRParam;

public class ZRApiInfo {
	private static final List<String> DEF_METHODS = Arrays.asList("GET", "POST");

	protected String version;
	protected String methodName;
	protected String returnType;
	protected List<ZRParamInfo> params;
	protected String author;
	protected String date;
	protected String name;
	protected String description;
	protected ZRAuthorityInfo[] defAuthoritys;
	protected List<String> uris;
	protected List<String> reqMethods;

	public ZRApiInfo() {
	}

	public ZRApiInfo(String methodName, String version, Method method) {
		this.version = version;
		this.methodName = methodName;
		this.returnType = method.getReturnType().getSimpleName();
		this.params = getParams(method);
		ZRAuthor author = method.getAnnotation(ZRAuthor.class);
		this.author = author == null ? null : author.value();
		ZRDate date = method.getAnnotation(ZRDate.class);
		this.date = date == null ? null : date.value();
		ZRDescription desc = method.getAnnotation(ZRDescription.class);
		this.name = desc == null ? null : desc.value();
		this.description = desc == null ? null : desc.description();
		this.defAuthoritys = getAuthoritys(method);
		this.uris = getUris(method);
		this.reqMethods = getReqMethods(method);
	}

	void setVersion(String version) {
		this.version = version;
	}

	void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	void setParams(List<ZRParamInfo> params) {
		this.params = params;
	}

	void setAuthor(String author) {
		this.author = author;
	}

	void setDate(String date) {
		this.date = date;
	}

	void setName(String name) {
		this.name = name;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setUris(List<String> uris) {
		this.uris = uris;
	}

	void setReqMethods(List<String> reqMethods) {
		this.reqMethods = reqMethods;
	}

	public String getVersion() {
		return version;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public List<ZRParamInfo> getParams() {
		return params;
	}

	public String getAuthor() {
		return author;
	}

	public String getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ZRAuthorityInfo[] getDefAuthoritys() {
		return defAuthoritys;
	}

	public void setDefAuthoritys(ZRAuthorityInfo[] defAuthoritys) {
		this.defAuthoritys = defAuthoritys;
	}

	public List<String> getUris() {
		return uris;
	}

	public List<String> getReqMethods() {
		return reqMethods;
	}

	private static final List<ZRParamInfo> getParams(Method method) {
		Parameter[] params = method.getParameters();
		if (params == null || params.length == 0)
			return Collections.emptyList();
		List<ZRParamInfo> paramList = new ArrayList<>(params.length);
		for (Parameter e : params) {
			ZRParamInfo info = new ZRParamInfo();
			info.setType(e.getType().getSimpleName());
			info.setName(e.getName());
			allowNull(info, e);
			ZRParam p = e.getAnnotation(ZRParam.class);
			info.setDescription(p == null ? null : p.value());
			info.setDemo(p == null ? null : p.demo());
			paramList.add(info);
		}
		return paramList;
	}

	private static final void allowNull(ZRParamInfo info, Parameter p) {
		Annotation anno;
		if ((anno = p.getAnnotation(RequestParam.class)) != null) {
			info.setParamType(ZRParamInfo.NORMAL_PARAM);
			info.setRequired(((RequestParam) anno).required());
		} else if ((anno = p.getAnnotation(PathVariable.class)) != null) {
			info.setParamType(ZRParamInfo.PATH_PARAM);
			info.setRequired(((PathVariable) anno).required());
		} else if ((anno = p.getAnnotation(CookieValue.class)) != null) {
			info.setParamType(ZRParamInfo.COOKIE_PARAM);
			info.setRequired(((CookieValue) anno).required());
		} else if ((anno = p.getAnnotation(RequestHeader.class)) != null) {
			info.setParamType(ZRParamInfo.HEADER_PARAM);
			info.setRequired(((RequestHeader) anno).required());
		} else {
			info.setParamType(ZRParamInfo.NORMAL_PARAM);
			info.setRequired(false);
		}
	}

	private static final List<String> getUris(Method method) {
		RequestMapping crm = method.getDeclaringClass().getAnnotation(RequestMapping.class);
		RequestMapping rm = method.getAnnotation(RequestMapping.class);
		String[] values;
		Set<String> parents = new HashSet<>();
		if (crm == null || (values = crm.value()) == null || values.length == 0)
			parents.add("");
		else
			for (String e : values) {
				if (e.isEmpty())
					parents.add(e);
				else {
					if (e.charAt(0) != '/')
						e = '/' + e;
					if (e.charAt(e.length() - 1) == '/')
						e = e.substring(0, e.length() - 1);
					parents.add(e);
				}
			}
		values = rm.value();
		if (values == null || values.length == 0)
			return new ArrayList<>(parents);
		Set<String> uris = new HashSet<>();
		for (String e : values) {
			if (e.isEmpty())
				continue;
			if (e.charAt(0) != '/')
				e = '/' + e;
			uris.add(e);
		}
		List<String> list = new ArrayList<>();
		for (String p : parents)
			for (String u : uris)
				list.add(p + u);
		return list;

	}

	private static final List<String> getReqMethods(Method method) {
		RequestMapping rm = method.getAnnotation(RequestMapping.class);
		RequestMethod[] methods = rm.method();
		if (methods == null || methods.length == 0)
			return DEF_METHODS;
		List<String> list = new ArrayList<>();
		for (RequestMethod e : methods)
			list.add(e.toString());
		return list;
	}

	private static final ZRAuthorityInfo[] getAuthoritys(Method method) {
		Set<String> set = new HashSet<>();
		getAuthoritys(method.getDeclaringClass(), set);
		getAuthoritys(method.getAnnotation(ZRAuthority.class), set);
		List<ZRAuthorityInfo> hr = new ArrayList<>(set.size());
		for (String e : set) {
			ZRAuthorityInfo info = ZRAuthorityInfo.parse(e);
			if (info != null)
				hr.add(info);
		}
		return hr.toArray(new ZRAuthorityInfo[hr.size()]);
	}

	private static final void getAuthoritys(Class<?> clazz, Set<String> set) {
		if (clazz.getSuperclass() != Object.class)
			getAuthoritys(clazz.getSuperclass(), set);
		getAuthoritys(clazz.getAnnotation(ZRAuthority.class), set);
	}

	private static final void getAuthoritys(ZRAuthority authority, Set<String> set) {
		if (authority == null)
			return;
		if (!authority.inherit())
			set.clear();
		for (String e : authority.values())
			set.add(e);
	}

}
