package zr.monitor.method;

public interface ZRMethodListener {
	public static final ZRMethodListener DEF = new ZRMethodListener() {
		@Override
		public void onMethod(ZRMethod method) {
		}
	};

	public void onMethod(ZRMethod method);
}
