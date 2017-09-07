package zr.monitor.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import v.common.unit.DefEnumeration;
import zr.monitor.ZRRequestFilter;
import zr.monitor.annotation.ZRFilter;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.info.ZRMethodSettings;
import zr.monitor.info.ZRMethodVersionSettings;
import zr.monitor.util.ZRMonitorUtil;

public class ZRMethodMgr {
	protected final ZRInfoMgr infoMgr;
	protected final ZRFliterMgr filterMgr;
	protected final Map<String, ZRMethod> methodMap0;
	protected final Map<Method, ZRMethod> methodMap1;
	protected final ZRMethodHandler handler;
	protected final ConcurrentLinkedQueue<ZRMethod> methods;

	public ZRMethodMgr(ZRInfoMgr infoMgr, ZRFliterMgr filterMgr, ZRMethodHandler handler) {
		this.infoMgr = infoMgr;
		this.filterMgr = filterMgr;
		this.handler = handler;
		this.methodMap0 = new HashMap<>();
		this.methodMap1 = new IdentityHashMap<>();
		this.methods = new ConcurrentLinkedQueue<>();
	}

	public Enumeration<ZRMethod> enumMethod() {
		return new DefEnumeration<>(methods.iterator());
	}

	public ZRMethod addMethod(Method method) {
		ZRMethod m = methodMap1.get(method);
		if (m == null)
			synchronized (this) {
				if ((m = methodMap1.get(method)) == null)
					methodMap1.put(method, m = createZRMethod(method));
			}
		return m;
	}

	public ZRMethod getMethod(Method method) {
		return methodMap1.get(method);
	}

	public ZRMethod getMethod(String methodName, String version) {
		return methodMap0.get(ZRMonitorUtil.getApiKey(methodName, version));
	}

	private ZRMethod createZRMethod(Method method) {
		ZRApiInfo info = infoMgr.addGetApiInfo(method);
		ZRMethodSettings settings = infoMgr.getApiSettings(info.getMethodName());
		ZRMethodVersionSettings versionSettings = infoMgr.getApiVersionSettings(info.getMethodName(),
				info.getVersion());
		ZRMethod m = new ZRMethod(method, info, settings, versionSettings, getFilters(method));
		String key = ZRMonitorUtil.getApiKey(info.getMethodName(), info.getVersion());
		methodMap0.put(key, m);
		methodMap1.put(method, m);
		methods.add(m);
		if (handler != null)
			handler.onMethod(m);
		return m;
	}

	private ZRRequestFilter[] getFilters(Method method) {
		ZRFilter anno = method.getDeclaringClass().getAnnotation(ZRFilter.class);
		List<Class<?>> list = new ArrayList<>();
		if (anno != null)
			for (Class<?> e : anno.value())
				if (!list.contains(e))
					list.add(e);
		anno = method.getAnnotation(ZRFilter.class);
		if (anno != null)
			for (Class<?> e : anno.value())
				if (!list.contains(e))
					list.add(e);
		ZRRequestFilter[] filters = new ZRRequestFilter[list.size()];
		int i = 0;
		for (Class<?> e : list)
			filters[i++] = filterMgr.getFilter(e);
		return filters;

	}

}
