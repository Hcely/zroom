package zr.monitor.method;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

import zr.monitor.ZRRequestFilter;
import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.info.ZRDomainAuthority;
import zr.monitor.info.ZRMethodSettings;
import zr.monitor.info.ZRMethodVersionSettings;

@SuppressWarnings("unchecked")
public class ZRMethod {
	private static int incNum = 0;

	private static final synchronized int getNumId() {
		return incNum++;
	}

	protected final int id = getNumId();
	protected final String version;
	protected final String methodName;
	protected final Method method;
	protected final ZRDomainAuthority[] defAuthoritys;
	protected final ZRMethodSettings settings;
	protected final ZRMethodVersionSettings versionSettings;
	protected final AtomicLong count;
	protected volatile ZRRequestFilter[] filters;
	protected Object flag0, flag1;

	ZRMethod(Method method, ZRApiInfo info, ZRMethodSettings settings, ZRMethodVersionSettings versionSettings,
			ZRRequestFilter[] filters) {
		this.version = info.getVersion();
		this.methodName = info.getMethodName();
		this.method = method;
		this.defAuthoritys = ZRDomainAuthority.parseList(info.getDefAuthoritys());
		this.settings = settings;
		this.versionSettings = versionSettings;
		this.count = new AtomicLong(0);
		this.filters = filters;
	}

	public int getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getMethodName() {
		return methodName;
	}

	public Method getMethod() {
		return method;
	}

	public <T> T getFlag0() {
		return (T) flag0;
	}

	public void setFlag0(Object flag0) {
		this.flag0 = flag0;
	}

	public <T> T getFlag1() {
		return (T) flag1;
	}

	public void setFlag1(Object flag1) {
		this.flag1 = flag1;
	}

	public boolean isOpen() {
		return settings.isOpen() && versionSettings.isOpen();
	}

	public int getTopology() {
		return versionSettings.getTopology();
	}

	public long incCount() {
		return count.incrementAndGet();
	}

	public ZRDomainAuthority[] getAuthoritys() {
		ZRDomainAuthority[] hr = versionSettings.getAuthoritys();
		if (hr != null)
			return hr.length > 0 ? hr : defAuthoritys;
		if ((hr = settings.getAuthoritys()) != null && hr.length > 0)
			return hr;
		return defAuthoritys;
	}

	public ZRDomainAuthority[] getApiAuthoritys() {
		return settings.getAuthoritys();
	}

	public ZRDomainAuthority[] getApiVersionAuthoritys() {
		return versionSettings.getAuthoritys();
	}

	public ZRDomainAuthority[] getDefAuthoritys() {
		return defAuthoritys;
	}

	public ZRRequestFilter[] getFilters() {
		return filters;
	}

	public void setFilters(ZRRequestFilter[] filters) {
		this.filters = filters == null ? ZRFilters.EMPTY : filters;
	}

}
