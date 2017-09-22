package zr.common.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ZRKey<V> {
	private static int incId = 0;
	private static final Map<String, ZRKey> keyMap0 = new HashMap<>();
	private static final Map<Integer, ZRKey> keyMap1 = new HashMap<>();

	public static final <V> ZRKey<V> build(String name) {
		ZRKey key = keyMap0.get(name);
		if (key == null)
			synchronized (ZRKey.class) {
				if ((key = keyMap0.get(name)) == null) {
					key = new ZRKey(name);
					keyMap0.put(name, key);
					keyMap1.put(key.id, key);
				}
			}
		return key;
	}

	public static final <V> ZRKey<V> parse(int idx) {
		return keyMap1.get(idx);
	}

	public final int id;
	public final String name;

	private ZRKey(String name) {
		this.id = incId++;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
