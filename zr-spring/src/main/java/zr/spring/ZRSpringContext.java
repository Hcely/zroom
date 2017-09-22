package zr.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zr.monitor.method.ZRMethod;

public class ZRSpringContext {
	protected static final ThreadLocal<ZRHttpRequest> requestTl = new ThreadLocal<ZRHttpRequest>() {
		@Override
		protected ZRHttpRequest initialValue() {
			return new ZRHttpRequest();
		}
	};

	static final ZRHttpRequest getRequest(long startTime, ZRMethod method, String remoteIp, HttpServletRequest request,
			HttpServletResponse response) {
		ZRHttpRequest zreq = requestTl.get();
		return zreq.set(startTime, method, remoteIp, request, response);
	}

	public static final ZRHttpRequest curZRequest() {
		ZRHttpRequest zreq = requestTl.get();
		return zreq.status ? zreq : null;
	}

	public static final HttpServletRequest curRequest() {
		if (ZRMultipartResolver.instance == null)
			return curRawRequest();
		HttpServletRequest req = ZRMultipartResolver.instance.curRequest();
		if (req == null)
			req = curRawRequest();
		return req;
	}

	public static final HttpServletRequest curRawRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static final HttpServletResponse curResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

}
