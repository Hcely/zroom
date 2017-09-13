package zr.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ZRMultipartResolver extends CommonsMultipartResolver {
	protected static ZRMultipartResolver instance;
	private final ThreadLocal<MultipartHttpServletRequest> requestTL;

	public ZRMultipartResolver() {
		super();
		requestTL = new ThreadLocal<>();
		instance = this;
	}

	public ZRMultipartResolver(ServletContext servletContext) {
		super(servletContext);
		requestTL = new ThreadLocal<>();
		instance = this;
	}

	public MultipartHttpServletRequest curRequest() {
		return requestTL.get();
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		MultipartHttpServletRequest result = super.resolveMultipart(request);
		requestTL.set(result);
		return result;
	}

	@Override
	public void cleanupMultipart(MultipartHttpServletRequest request) {
		requestTL.remove();
		super.cleanupMultipart(request);
	}

}