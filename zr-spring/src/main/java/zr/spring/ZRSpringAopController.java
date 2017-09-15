package zr.spring;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import v.Initializable;
import zr.monitor.ZRMonitorCenter;

public class ZRSpringAopController implements ApplicationContextAware, Initializable {
	protected ApplicationContext appContext;
	protected ZRMonitorCenter center;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	@PostConstruct
	@Override
	public void init() {

	}

	@Around("execution(* *..*Controller.*(..))")
	public Object execute(ProceedingJoinPoint jp) throws Throwable {
		center.execute(invoker, method, remoteIp, request, response);
	}

}
