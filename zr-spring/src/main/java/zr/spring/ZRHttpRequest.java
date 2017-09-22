package zr.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import v.common.helper.ParseUtil;
import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRequest;
import zr.monitor.method.ZRMethod;

public class ZRHttpRequest extends ZRequest {
	protected boolean status;
	protected String remoteIp;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	ZRHttpRequest() {
		status = false;
	}

	protected ZRHttpRequest set(long startTime, ZRMethod method, String remoteIp, HttpServletRequest request,
			HttpServletResponse response) {
		this.status = true;
		this.remoteIp = remoteIp;
		this.request = request;
		this.response = response;
		super.set(startTime, method);
		return this;
	}

	@Override
	protected void reset() {
		super.reset();
		this.status = false;
		this.remoteIp = null;
		this.request = null;
		this.response = null;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public String getRemoteIp() {
		return remoteIp;
	}

	@Override
	public String getReqId() {
		return request.getHeader(ZRMonitorCenter.ZR_REQUEST_ID);
	}

	@Override
	public String getReqPrevId() {
		return request.getHeader(ZRMonitorCenter.ZR_REQUEST_PREV_ID);
	}

	@Override
	public String getReqSilkId() {
		return request.getHeader(ZRMonitorCenter.ZR_REQUEST_SILK_ID);
	}

	@Override
	public boolean isReqHeaderNode() {
		String str = request.getHeader(ZRMonitorCenter.ZR_REQUEST_HEADER_NODE);
		return ParseUtil.parseBoolean(str, Boolean.FALSE);
	}

}
