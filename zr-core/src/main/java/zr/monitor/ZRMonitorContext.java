package zr.monitor;

public class ZRMonitorContext {
	protected static final ThreadLocal<ZRRequest> requestTL = new ThreadLocal<ZRRequest>() {
		@Override
		protected ZRRequest initialValue() {
			return new ZRRequest();
		}
	};

	public static final ZRRequest getCurRequest() {
		return requestTL.get();
	}

	protected static final ThreadLocal<ZRTopology> topologyTL = new ThreadLocal<ZRTopology>() {
		@Override
		protected ZRTopology initialValue() {
			return new ZRTopology();
		}
	};

}
