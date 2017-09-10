package zr.monitor.info;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRParamInfo;

public interface ZRApiInfoBuilder {
	public static final ZRApiInfoBuilder DEF = new ZRApiInfoBuilder() {

		@Override
		public ZRApiInfo build(String methodName, String version, Method method) {
			return new ZRApiInfo(methodName, version, method);
		}

		@Override
		public ZRParamInfo build(Parameter param) {
			return new ZRParamInfo(param);
		}
	};

	public ZRApiInfo build(String methodName, String version, Method method);

	public ZRParamInfo build(Parameter param);
}
