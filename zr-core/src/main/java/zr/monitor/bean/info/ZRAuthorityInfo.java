package zr.monitor.bean.info;

import java.util.ArrayList;
import java.util.List;

import v.common.helper.StrUtil;

public final class ZRAuthorityInfo {
	public static final ZRAuthorityInfo[] parseList(String str) {
		String[] strs = StrUtil.spilt(str, ',');
		List<ZRAuthorityInfo> list = new ArrayList<>(strs.length);
		for (String s : strs) {
			ZRAuthorityInfo a = parse(s);
			if (a != null)
				list.add(a);
		}
		return list.toArray(new ZRAuthorityInfo[list.size()]);
	}

	public static final ZRAuthorityInfo parse(String str) {
		String[] strs = StrUtil.spilt(str, ':', 2);
		if (strs.length != 2)
			return null;
		return new ZRAuthorityInfo(strs[0], strs[1]);
	}

	protected String domain;
	protected String authority;

	ZRAuthorityInfo() {
	}

	public ZRAuthorityInfo(String domain, String authority) {
		this.domain = domain;
		this.authority = authority;
	}

	void setDomain(String domain) {
		this.domain = domain;
	}

	void setAuthority(String authority) {
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
		return "ZRAuthority [domain=" + domain + ", authority=" + authority + "]";
	};

}
