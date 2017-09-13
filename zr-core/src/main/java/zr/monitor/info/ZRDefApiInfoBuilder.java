package zr.monitor.info;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import v.server.helper.ClassHelper;
import zr.monitor.annotation.ZRAuthor;
import zr.monitor.annotation.ZRAuthority;
import zr.monitor.annotation.ZRDate;
import zr.monitor.annotation.ZRDescription;
import zr.monitor.annotation.ZRFilter;
import zr.monitor.annotation.ZRRequiedParam;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRAuthorityInfo;
import zr.monitor.bean.info.ZRParamInfo;

public class ZRDefApiInfoBuilder implements ZRApiInfoBuilder {
	public static final ZRDefApiInfoBuilder INSTANCE = new ZRDefApiInfoBuilder();

	@Override
	public final ZRApiInfo buildApiInfo(String methodName, String version, Method method) {
		ZRApiInfo apiInfo = createApiInfo();
		setBaseInfo(methodName, version, method, apiInfo);
		LinkedHashMap<String, ZRParamInfo> paramsMap = new LinkedHashMap<>();
		getZRRequiedParams(method, paramsMap);
		getMethodParams(method, paramsMap);
		ZRParamInfo[] params = paramsMap.values().toArray(new ZRParamInfo[paramsMap.size()]);
		apiInfo.setParams(params);
		return apiInfo;
	}

	protected ZRApiInfo createApiInfo() {
		return new ZRApiInfo();
	}

	protected void setBaseInfo(String methodName, String version, Method method, ZRApiInfo apiInfo) {
		apiInfo.setVersion(version);
		apiInfo.setMethodName(methodName);
		apiInfo.setReturnType(method.getReturnType().getSimpleName());
		ZRAuthor author = method.getAnnotation(ZRAuthor.class);
		if (author != null)
			apiInfo.setAuthor(author.value());
		ZRDate date = method.getAnnotation(ZRDate.class);
		if (date != null)
			apiInfo.setDate(date.value());
		ZRDescription desc = method.getAnnotation(ZRDescription.class);
		if (desc != null) {
			apiInfo.setName(desc.value());
			apiInfo.setDescription(desc.description());
		}
		apiInfo.setDefAuthoritys(getAuthoritys(method));
	}

	protected void getZRRequiedParams(Method method, LinkedHashMap<String, ZRParamInfo> hr) {
		ZRFilter anno = method.getDeclaringClass().getAnnotation(ZRFilter.class);
		getZRRequiedParams(anno, hr);
		anno = method.getAnnotation(ZRFilter.class);
		getZRRequiedParams(anno, hr);
	}

	protected final void getZRRequiedParams(ZRFilter anno, LinkedHashMap<String, ZRParamInfo> hr) {
		if (anno == null)
			return;
		for (Class<?> clazz : anno.value()) {
			ZRRequiedParam p = clazz.getAnnotation(ZRRequiedParam.class);
			for (String e : p.value()) {
				ZRParamInfo info = ZRParamInfo.parseStr(e);
				if (info != null)
					hr.put(info.getName(), info);
			}
		}
	}

	protected void getMethodParams(Method method, LinkedHashMap<String, ZRParamInfo> hr) {
		Parameter[] params = method.getParameters();
		for (Parameter e : params)
			parseParams(e, hr);
	}

	protected void parseParams(Parameter param, LinkedHashMap<String, ZRParamInfo> hr) {
		Class<?> clazz = param.getType();
		if (isBaseType(clazz)) {
			ZRParamInfo info = ZRParamInfo.parse(param);
			hr.put(info.getName(), info);
		} else {
			Map<String, Field> map = ClassHelper.getAllFields(clazz);
			for (Field f : map.values()) {
				int mod = f.getModifiers();
				if (Modifier.isFinal(mod) || Modifier.isStatic(mod))
					continue;
				ZRParamInfo info = ZRParamInfo.parse(f);
				hr.put(info.getName(), info);
			}
		}
	}

	private static final ZRAuthorityInfo[] getAuthoritys(Method method) {
		Set<String> set = new HashSet<>();

		getAuthoritys(method.getDeclaringClass().getAnnotation(ZRAuthority.class), set);
		getAuthoritys(method.getAnnotation(ZRAuthority.class), set);

		List<ZRAuthorityInfo> hr = new ArrayList<>(set.size());
		for (String e : set) {
			ZRAuthorityInfo info = ZRAuthorityInfo.parse(e);
			if (info != null)
				hr.add(info);
		}
		return hr.toArray(new ZRAuthorityInfo[hr.size()]);
	}

	private static final void getAuthoritys(ZRAuthority authority, Set<String> set) {
		if (authority == null)
			return;
		for (String e : authority.values())
			set.add(e);
	}

	public static final boolean isBaseType(Class<?> clazz) {
		if (!Object.class.isAssignableFrom(clazz))
			return true;
		if (clazz == Boolean.class)
			return true;
		if (clazz == Byte.class)
			return true;
		if (clazz == Short.class)
			return true;
		if (clazz == Character.class)
			return true;
		if (clazz == Integer.class)
			return true;
		if (clazz == Long.class)
			return true;
		if (clazz == Float.class)
			return true;
		if (clazz == Double.class)
			return true;
		if (CharSequence.class.isAssignableFrom(clazz))
			return true;
		if (Collection.class.isAssignableFrom(clazz))
			return true;
		if (Map.class.isAssignableFrom(clazz))
			return true;
		return false;
	}

}
