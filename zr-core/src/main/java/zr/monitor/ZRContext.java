package zr.monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zr.monitor.method.ZRMethod;
import zr.monitor.util.ZRMonitorUtil;

public class ZRContext {
	private static final ThreadLocal<ZRRequest> requestTL = new ThreadLocal<ZRRequest>() {
		@Override
		protected ZRRequest initialValue() {
			return ZRRequest.create();
		}
	};

	protected static final ZRRequest getRequest(ZRMethod method, String removeIp, HttpServletRequest request,
			HttpServletResponse response) {
		return requestTL.get().set(method, removeIp, request, response);
	}

	public static final ZRRequest getCurRequest() {
		return requestTL.get();
	}

	private static final ThreadLocal<ZRTopologyRef> topologyTL = new ThreadLocal<ZRTopologyRef>() {
		@Override
		protected ZRTopologyRef initialValue() {
			return new ZRTopologyRef();
		}
	};

	protected static final ZRTopology getTopology(String reqId, String prevId, String methodName, String version,
			int num, long startTime) {
		String silkId = prevId == null ? ZRMonitorUtil.buildSilkId(methodName.hashCode(), num)
				: ZRMonitorUtil.buildSilkId(prevId.hashCode(), num);
		return topologyTL.get().setTopology(reqId, prevId, silkId, methodName, version, startTime);
	}

	protected static final void removeTopology() {
		topologyTL.get().reset();
	}

	public static final ZRTopology curTopology() {
		return topologyTL.get().getTopology();
	}

	private static final class ZRTopologyRef {
		public boolean hasTopology = false;
		public final ZRTopology topology = ZRTopology.create();

		public final void reset() {
			hasTopology = false;
			topology.reset();
		}

		public final ZRTopology setTopology(final String reqId, final String prevId, final String silkId,
				final String methodName, final String version, final long startTime) {
			hasTopology = true;
			return topology.set(reqId, prevId, silkId, methodName, version, startTime);
		}

		public final ZRTopology getTopology() {
			return hasTopology ? topology : null;
		}
	}

}
