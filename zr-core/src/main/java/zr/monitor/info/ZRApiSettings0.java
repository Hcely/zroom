package zr.monitor.info;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import zr.monitor.bean.info.ZRApiSettings;

final class ZRApiSettings0 {
	protected boolean open;
	protected final Map<String, Void> authorityMap;

	ZRApiSettings0() {
		this.open = true;
		this.authorityMap = new LinkedHashMap<>();
	}

	public void set(ZRApiSettings settings) {
		if (settings == null)
			return;
		if (!settings.isOpen())
			open = false;
		List<String> authoritys = settings.getAuthoritys();
		if (authoritys == null)
			return;
		for (String e : authoritys)
			authorityMap.put(e, null);
	}

	public ZRDomainAuthority[] toAuthoritys() {
		return ZRDomainAuthority.parseList(authorityMap.keySet());
	}

}
