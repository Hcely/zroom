package zr.spring;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import v.Initializable;
import v.server.helper.ClassHelper;
import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRRequestFilter;
import zr.monitor.info.ZRApiInfoBuilder;
import zr.monitor.method.ZRMethod;
import zr.monitor.method.ZRMethodListener;
import zr.monitor.statistic.ZRStatisticHandler;
import zr.spring.util.ZRSpringUtil;

public class ZRSpringAopController implements ApplicationContextAware, Initializable {
	private static final String CONTROLLER_FLAG = "controller";
	protected ApplicationContext appContext;
	protected final ZRMonitorCenter center;
	protected final Map<Method, ZRMethod> methodMapping;

	public ZRSpringAopController() {
		this.center = new ZRMonitorCenter();
		this.methodMapping = new IdentityHashMap<>(256);
	}

	public void setWorkerNum(int workerNum) {
		center.setWorkerNum(workerNum);
	}

	public void setCacheSize(int cacheSize) {
		center.setCacheSize(cacheSize);
	}

	public void setApiInfoBuilder(ZRApiInfoBuilder apiInfoBuilder) {
		center.setApiInfoBuilder(apiInfoBuilder);
	}

	public void setMethodListener(ZRMethodListener methodListener) {
		center.setMethodListener(methodListener);
	}

	public void setStatisticHandler(ZRStatisticHandler statisticHandler) {
		center.setStatisticHandler(statisticHandler);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	@PostConstruct
	@Override
	public void init() {

		center.setFilters(getFilters());
		center.setMethods(getMethods());
		center.setApiInfoBuilder(getApiInfoBuilder());

	}

	protected Set<Method> getMethods() {
		Map<String, Object> beans = appContext.getBeansWithAnnotation(Controller.class);
		Map<Class<?>, Void> controllerClasses = new IdentityHashMap<>();
		for (Object e : beans.values()) {
			Class<?> clazz = e.getClass();
			if (!clazz.getName().contains(CONTROLLER_FLAG))
				continue;
			clazz = ZRSpringUtil.getRawClass(clazz);
			controllerClasses.put(clazz, null);
		}
		Map<Method, Void> methods = new IdentityHashMap<>();
		for (Class<?> clazz : controllerClasses.keySet()) {
			Map<String, Method> ms = ClassHelper.getAllMethods(clazz);
			for (Method e : ms.values()) {
				int mod = e.getModifiers();
				if (Modifier.isStatic(mod))
					continue;
				if (e.getAnnotation(RequestMapping.class) == null)
					continue;
				methods.put(e, null);
			}
		}
		return new HashSet<>(methods.keySet());
	}

	protected Set<ZRRequestFilter> getFilters() {
		Map<String, ZRRequestFilter> beans = appContext.getBeansOfType(ZRRequestFilter.class);
		return new HashSet<>(beans.values());
	}

	protected ZRApiInfoBuilder getApiInfoBuilder() {
		return new ZRSpringApiInfoBuilder();
	}

	@Around("execution(* *..controller..*.*(..))")
	public Object execute(ProceedingJoinPoint jp) throws Throwable {
		MethodSignature ms = (MethodSignature) jp.getSignature();
		ZRMethod zrm = getZRMethod(ms.getMethod());
		HttpServletRequest request = getCurHttpRequest();
		HttpServletResponse response = getCurHttpResponse();
		String remoteIp = getRemoteIp(request);
		return center.execute(jp, zrm, remoteIp, request, response);
	}

	protected ZRMethod getZRMethod(Method method) {
		ZRMethod zrm = methodMapping.get(method);
		if (zrm == null)
			synchronized (this) {
				if ((zrm = methodMapping.get(method)) == null) {
					zrm = center.getZRMethod(ZRSpringUtil.getRawMethod(method));
					if (zrm == null)
						zrm = ZRMethod.NULL;
					methodMapping.put(method, zrm);
				}
			}
		return zrm;
	}

	protected HttpServletRequest getCurHttpRequest() {
		return ZRSpringContext.curRequest();
	}

	protected HttpServletResponse getCurHttpResponse() {
		return ZRSpringContext.curResponse();
	}

	protected String getRemoteIp(HttpServletRequest request) {
		String hr = request.getRemoteAddr();
		if (hr != null && !"127.0.0.1".equals(hr))
			return hr;
		hr = request.getHeader("X-Forwarded-For");
		if (hr != null)
			return hr;
		hr = request.getHeader("X-Real-IP");
		if (hr != null)
			return hr;
		return "127.0.0.1";
	}

}
