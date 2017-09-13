package zr.monitor.statistic;

import zr.monitor.ZRRequest;
import zr.monitor.bean.result.ZRRequestResult;
import zr.monitor.method.ZRMethod;

final class ZRRequestResult0 extends ZRRequestResult {
	ZRRequestResult0() {
	}

	void set(String reqId, ZRMethod zrm, ZRRequest request) {
		this.version = zrm.getVersion();
		this.methodName = zrm.getMethodName();
		this.remoteIp = request.getRemoveIp();
		this.startTime = request.getStartTime();
		this.take = request.getTake();
		this.resultStatus = request.getResultStatus();
		this.reqId = reqId;
		this.error = request.getError();
	}

	void reset() {
		this.version = null;
		this.methodName = null;
		this.remoteIp = null;
		this.reqId = null;
		this.logContent = null;
		this.flags = null;
		this.error = null;
	}
}
