package zr.monitor.util;

import java.util.Enumeration;

import v.common.util.AutoArray;

public class ZRApiCounts {
	protected final AutoArray<ZRApiCount> counts;

	public ZRApiCounts() {
		this.counts = new AutoArray<>(128, 16);
	}

	public void add(ZRApiCounts count) {
		AutoArray<ZRApiCount> c = count.counts;
		for (int i = 0, len = c.length(); i < len; ++i) {
			ZRApiCount e = c.opt(i);
			if (e != null)
				getCount(i, e.methodName, e.version).add(e);
		}
	}

	public void add(int id, String methodName, String version, long take, int respType) {
		getCount(id, methodName, version).add(take, respType);
	}

	public Enumeration<ZRApiCount> enumeration() {
		return counts.enumeration();
	}

	private ZRApiCount getCount(int id, String methodName, String version) {
		ZRApiCount c = counts.opt(id);
		if (c == null) {
			c = new ZRApiCount(methodName, version);
			counts.set(id, c);
		}
		return c;
	}

	public ZRApiCounts reset() {
		for (Enumeration<ZRApiCount> it = counts.enumeration(); it.hasMoreElements();)
			it.nextElement().reset();
		return this;
	}

}
