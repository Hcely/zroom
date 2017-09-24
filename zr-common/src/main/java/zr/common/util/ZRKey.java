package zr.common.util;

import java.util.HashMap;
import java.util.Map;

import v.common.util.AutoArray;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ZRKey<V> {
	private static int incId = 0;
	private static final Map<String, ZRKey> keyMap0 = new HashMap<>();
	private static final AutoArray<ZRKey> keyMap1 = new AutoArray<>(8, 8);

	public static final <V> ZRKey<V> build(String name, Class<V> clazz) {
		ZRKey key = keyMap0.get(name);
		if (key == null)
			synchronized (ZRKey.class) {
				if ((key = keyMap0.get(name)) == null) {
					key = new ZRKey(name);
					keyMap0.put(name, key);
					keyMap1.set(key.id, key);
				}
			}
		return key;
	}

	public static final <V> ZRKey<V> parse(int idx) {
		return keyMap1.opt(idx);
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
