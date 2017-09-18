package zr.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import v.common.helper.StrUtil;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRParamInfo;
import zr.monitor.info.ZRDefApiInfoBuilder;
import zr.spring.util.ZRSpringUtil;

public class ZRSpringApiInfoBuilder extends ZRDefApiInfoBuilder {

	@Override
	protected void setBaseInfo(String module, String methodName, String version, Method method, ZRApiInfo apiInfo) {
		super.setBaseInfo(module, methodName, version, method, apiInfo);
		apiInfo.setUris(ZRSpringUtil.getUris(method));
		apiInfo.setMethods(ZRSpringUtil.getMethods(method));
	}

	@Override
	protected ZRParamInfo parseParam(Parameter param) {
		ZRParamInfo info = super.parseParam(param);
		Annotation anno;
		if ((anno = param.getAnnotation(RequestParam.class)) != null) {
			info.setOrgType(ZRParamInfo.ORG_NORMAL);
			RequestParam a = (RequestParam) anno;
			if (!StrUtil.isEmpty(a.value()))
				info.setName(a.value());
			else if (!StrUtil.isEmpty(a.name()))
				info.setName(a.name());
			info.setRequired(a.required());
		} else if ((anno = param.getAnnotation(PathVariable.class)) != null) {
			info.setOrgType(ZRParamInfo.ORG_PATH);
			PathVariable a = (PathVariable) anno;
			if (!StrUtil.isEmpty(a.value()))
				info.setName(a.value());
			else if (!StrUtil.isEmpty(a.name()))
				info.setName(a.name());
			info.setRequired(a.required());
		} else if ((anno = param.getAnnotation(RequestHeader.class)) != null) {
			info.setOrgType(ZRParamInfo.ORG_HEADER);
			RequestHeader a = (RequestHeader) anno;
			if (!StrUtil.isEmpty(a.value()))
				info.setName(a.value());
			else if (!StrUtil.isEmpty(a.name()))
				info.setName(a.name());
			info.setRequired(a.required());
		} else if ((anno = param.getAnnotation(CookieValue.class)) != null) {
			info.setOrgType(ZRParamInfo.ORG_COOKIE);
			CookieValue a = (CookieValue) anno;
			if (!StrUtil.isEmpty(a.value()))
				info.setName(a.value());
			else if (!StrUtil.isEmpty(a.name()))
				info.setName(a.name());
			info.setRequired(a.required());
		}
		return info;
	}

}
