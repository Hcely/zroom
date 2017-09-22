package org.zr.common;

import v.common.util.AutoArray;
import zr.common.util.ZRKey;
import zr.common.util.ZRKeyMap;

@SuppressWarnings("unchecked")
public class Test01 {
	public static final int len = 20;
	public static final int size = 100000000;

	public static void main(String[] args) {
		ZRKey<String>[] keys = new ZRKey[len];
		for (int i = 0; i < len; ++i) {
			keys[i] = ZRKey.build("key" + i);
		}
		run0(keys);
		System.out.println("=======");
		run1(keys);
	}

	private static final void run0(final ZRKey<String>[] keys) {
		ZRKeyMap map = new ZRKeyMap();
		long l;
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
	}

	private static final void run1(final ZRKey<String>[] keys) {
		AutoArray<Object> map = new AutoArray<>();
		long l;
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);
		l = System.currentTimeMillis();
		for (int i = 0; i < size; ++i) {
			for (ZRKey<String> key : keys)
				map.set(key.id, "aaaa");
			map.clear();
		}
		System.out.println(System.currentTimeMillis() - l);

	}

}
