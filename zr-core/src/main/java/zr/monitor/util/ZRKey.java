package zr.monitor.util;

import java.util.HashMap;
import java.util.Map;

public final class ZRKey {
	private static int incId = 0;
	private static final Map<String, ZRKey> keyMap = new HashMap<>();

	public static final ZRKey build(String name) {
		ZRKey key = keyMap.get(name);
		if (key == null)
			synchronized (ZRKey.class) {
				if ((key = keyMap.get(name)) == null) {
					key = new ZRKey(name);
					keyMap.put(name, key);
				}
			}
		return key;
	}

	protected final int id;
	protected final String name;

	private ZRKey(String name) {
		this.id = incId++;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
