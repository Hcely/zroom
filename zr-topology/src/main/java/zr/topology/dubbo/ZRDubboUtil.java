package zr.topology.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import v.common.helper.StrUtil;

class ZRDubboUtil {
	public static final String getMethodName(Invoker<?> invoker, Invocation invocation) {
		String className = invoker.getInterface().getName();
		String methodName = invocation.getMethodName();
		StringBuilder sb = new StringBuilder(className.length() + methodName.length() + 1);
		sb.append(className).append('.').append(methodName);
		return StrUtil.sbToString(sb);
	}
}
