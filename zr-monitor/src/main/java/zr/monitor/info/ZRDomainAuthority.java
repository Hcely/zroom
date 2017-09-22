package zr.monitor.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import v.common.helper.StrUtil;

public final class ZRDomainAuthority {
	private static final ZRDomainAuthority[] EMPTY = {};

	public static final ZRDomainAuthority parse(String str) {
		String[] strs = StrUtil.spilt(str, ':', 2);
		if (strs.length != 2)
			return null;
		return new ZRDomainAuthority(strs[0], strs[1]);
	}

	public static final ZRDomainAuthority[] parseList(Collection<String> strs) {
		if (strs == null || strs.isEmpty())
			return EMPTY;
		List<ZRDomainAuthority> list = new ArrayList<>(strs.size());
		for (String s : strs) {
			ZRDomainAuthority a = parse(s);
			if (a != null)
				list.add(a);
		}
		return list.toArray(new ZRDomainAuthority[list.size()]);
	}

	protected final String domain;
	protected final String authority;

	ZRDomainAuthority(String domain, String authority) {
		this.domain = domain;
		this.authority = authority;
	}

	public String getDomain() {
		return domain;
	}

	public String getAuthority() {
		return authority;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(domain.length() + 1 + authority.length());
		sb.append(domain).append(':').append(authority);
		return StrUtil.sbToString(sb);
	};

}
