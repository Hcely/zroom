package zr.spring;

import org.aspectj.lang.ProceedingJoinPoint;

import zr.monitor.ZRRequest;
import zr.monitor.ZRRequestHandler;

final class ZRSpringRequestHandler implements ZRRequestHandler<ProceedingJoinPoint> {

	@Override
	public Object executeNoMethod(ProceedingJoinPoint invoker) throws Throwable {
		return invoker.proceed();
	}

	@Override
	public void onApiClose(ProceedingJoinPoint invoker, ZRRequest zreq) throws Throwable {

	}

	@Override
	public boolean onBefore(ProceedingJoinPoint invoker, ZRRequest zreq) throws Throwable {

		return false;
	}

	@Override
	public void execute(ProceedingJoinPoint invoker, ZRRequest zreq) throws Throwable {

	}

	@Override
	public void onAfter(ProceedingJoinPoint invoker, ZRRequest zreq) {

	}

	@Override
	public String onLog(ProceedingJoinPoint invoker, ZRRequest zreq) {
		return null;
	}

	@Override
	public Throwable onError(ProceedingJoinPoint invoker, ZRRequest zreq, boolean resultError) {

		return null;
	}

}
