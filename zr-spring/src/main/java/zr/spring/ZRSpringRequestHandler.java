package zr.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.ModelAndView;

import zr.monitor.ZRRequestHandler;

public interface ZRSpringRequestHandler extends ZRRequestHandler<ProceedingJoinPoint, ZRHttpRequest> {
	@Override
	public default Object executeNoMethod(ProceedingJoinPoint invoker, ZRHttpRequest zreq) throws Throwable {
		return invoker.proceed();
	}

	@Override
	public default boolean onBefore(ProceedingJoinPoint invoker, ZRHttpRequest zreq) throws Throwable {
		return false;
	}

	@Override
	public default void onAfter(ProceedingJoinPoint invoker, ZRHttpRequest zreq) {
	}

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex);
}
