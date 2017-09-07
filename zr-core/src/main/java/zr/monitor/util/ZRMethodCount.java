package zr.monitor.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import v.common.unit.DefEnumeration;

public class ZRMethodCount {
	protected final String serverId;
	protected final Map<String, ZRCount> counts;

	ZRMethodCount(String serverId) {
		this.serverId = serverId;
		this.counts = new HashMap<>();
	}

	public void add(String name, long take, int respType) {
		getCount(name).add(take, respType);
	}

	public Enumeration<ZRCount> enumeration() {
		return new DefEnumeration<>(counts.values().iterator());
	}

	private ZRCount getCount(String name) {
		ZRCount c = counts.get(name);
		if (c == null) {
			c = new ZRCount(name);
			counts.put(name, c);
		}
		return c;
	}

	public void reset() {
		for (ZRCount c : counts.values())
			c.reset();
	}

}
