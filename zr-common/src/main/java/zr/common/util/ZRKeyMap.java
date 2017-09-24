package zr.common.util;

import java.util.Iterator;
import java.util.Map.Entry;

import v.Clearable;
import v.common.helper.NumberHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ZRKeyMap implements Clearable, Iterable<Entry<ZRKey, Object>> {
	private Object[] datas;
	private int capacity;
	private final int incSize;

	public ZRKeyMap() {
		this(8, 4);
	}

	public ZRKeyMap(int initSize, int incSize) {
		initSize = initSize < 8 ? 8 : initSize;
		incSize = incSize < 4 ? incSize : 4;
		this.datas = new Object[initSize];
		this.capacity = datas.length;
		this.incSize = incSize;
	}

	private void checkCapacity(final int idx) {
		if (idx < capacity)
			return;
		int newCapacity = NumberHelper.cell(idx + 1, incSize);
		Object[] newDatas = new Object[newCapacity];
		System.arraycopy(datas, 0, newDatas, 0, capacity);
		datas = newDatas;
		capacity = newCapacity;
	}

	public <V> V set(final ZRKey<V> key, final V value) {
		final int idx = key.id;
		checkCapacity(idx);
		Object v = datas[idx];
		datas[idx] = value;
		return (V) v;
	}

	public <V> V get(final ZRKey<V> key) {
		final int idx = key.id;
		if (idx < capacity)
			return (V) datas[idx];
		return null;
	}

	public <V> V remove(final ZRKey<V> key) {
		return set(key, null);
	}

	@Override
	public void clear() {
		for (int i = 0, len = capacity; i < len; ++i)
			datas[i] = null;
	}

	@Override
	public Iterator<Entry<ZRKey, Object>> iterator() {
		
		return null;
	}
	
	

}
