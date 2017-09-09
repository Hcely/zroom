package zr.monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zr.monitor.method.ZRMethod;

public class ZRContext {
	private static final ThreadLocal<ZRRequest> requestTL = new ThreadLocal<ZRRequest>() {
		@Override
		protected ZRRequest initialValue() {
			return new ZRRequest();
		}
	};

	protected static final ZRRequest getRequest(ZRMethod method, String removeIp, HttpServletRequest request,
			HttpServletResponse response) {
		return requestTL.get().set(method, removeIp, request, response);
	}

	public static final ZRRequest getCurRequest() {
		return requestTL.get();
	}

	private static final ThreadLocal<ZRTopologyStack> topologyTL = new ThreadLocal<ZRTopologyStack>() {
		@Override
		protected ZRTopologyStack initialValue() {
			return new ZRTopologyStack();
		}
	};

	public static final ZRTopologyStack curTopology() {
		return topologyTL.get();
	}

}
