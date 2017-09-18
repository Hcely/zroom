package zr.monitor.method;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import v.Clearable;
import v.common.unit.DefEnumeration;
import zr.monitor.ZRRequestFilter;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.info.ZRMethodSettings;
import zr.monitor.info.ZRMethodVersionSettings;

public class ZRMethodMgr implements Clearable {
	protected final ZRInfoMgr infoMgr;
	protected final ZRFilters filters;
	protected final Map<String, ZRMethod> methodMap0;
	protected final Map<Method, ZRMethod> methodMap1;
	protected final List<ZRMethod> methods;
	protected final ZRMethodListener listener;

	public ZRMethodMgr(ZRInfoMgr infoMgr, ZRMethodListener listener) {
		this.infoMgr = infoMgr;
		this.filters = new ZRFilters();
		this.listener = listener == null ? ZRMethodListener.DEF : listener;
		this.methodMap0 = new HashMap<>(256);
		this.methodMap1 = new IdentityHashMap<>(256);
		this.methods = new LinkedList<>();
	}

	@Override
	public void clear() {
		methodMap0.clear();
		methodMap1.clear();
		filters.clear();
		methods.clear();
	}

	public Enumeration<ZRMethod> enumMethod() {
		return new DefEnumeration<>(methods.iterator());
	}

	public void addFilters(Collection<ZRRequestFilter> filters) {
		this.filters.addFilters(filters);
	}

	public void addFilter(ZRRequestFilter filter) {
		filters.addFilter(filter);
	}

	public void addMethods(Collection<Method> methods) {
		if (methods != null)
			for (Method e : methods)
				addMethod(e);
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

	public ZRMethod getMethod(String methodName) {
		return methodMap0.get(methodName);
	}

	private ZRMethod createZRMethod(Method method) {
		ZRApiInfo info = infoMgr.addGetApiInfo(method);
		ZRMethodSettings settings = infoMgr.getApiSettings(info.getModule(), info.getMethodName());
		ZRMethodVersionSettings versionSettings = infoMgr.getApiVersionSettings(info.getMethodName(),
				info.getVersion());
		ZRRequestFilter[] filters = this.filters.getFilters(method);
		ZRMethod m = new ZRMethod(method, info, settings, versionSettings, filters);
		methodMap0.put(info.getMethodName(), m);
		methodMap1.put(method, m);
		methods.add(m);
		listener.onMethod(m);
		return m;
	}

}
