package zr.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class ZRSpringUtil {
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
