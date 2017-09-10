package zr.monitor.info;

import java.lang.reflect.Method;

import zr.monitor.bean.info.ZRApiInfo;

public interface ZRApiInfoBuilder {
	public static final ZRApiInfoBuilder DEF = new ZRApiInfoBuilder() {

		@Override
		public ZRApiInfo build(String methodName, String version, Method method) {
			return new ZRApiInfo(methodName, version, method);
		}
	};

	public ZRApiInfo build(String methodName, String version, Method method);
}
