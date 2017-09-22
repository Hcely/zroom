package zr.monitor.core;

import java.util.HashSet;
import java.util.Set;

import zr.monitor.util.ZRMonitorUtil;

public class Test01 {

	public static void main(String[] args) {
		String str = "000000-00";
		Set<String> set = new HashSet<>();
		for (int i = 0; i < 100; ++i) {
			str = ZRMonitorUtil.buildSilkId(str.hashCode(), i);
			set.add(str);
		}
		System.out.println(set.size());
	}

}
