package zr.monitor.info;

import java.lang.reflect.Method;

import zr.monitor.bean.info.ZRApiInfo;

public interface ZRInfoCreator {
	public ZRApiInfo createInfo(String methodName, String version, Method method);
}
