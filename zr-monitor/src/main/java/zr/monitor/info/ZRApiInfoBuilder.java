package zr.monitor.info;

import java.lang.reflect.Method;

import zr.monitor.bean.info.ZRApiInfo;

public interface ZRApiInfoBuilder {
	public ZRApiInfo buildApiInfo(String module, String methodName, String version, Method method);
}
