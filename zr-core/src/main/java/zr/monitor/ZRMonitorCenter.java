package zr.monitor;

import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import v.Initializable;
import v.VObject;
import v.common.unit.VThreadLoop;
import zr.monitor.cluster.ZRClusterServer;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.method.ZRMethodMgr;
import zr.monitor.statistic.ZRStatisticCenter;

public class ZRMonitorCenter implements Initializable, VObject {
	protected VThreadLoop loop;

	protected ZRInfoMgr infoMgr;
	protected ZRMethodMgr methodMgr;
	protected ZRStatisticCenter statisticCenter;

	protected ZRClusterServer clusterServer;
	protected Set<Method> methods;
	protected Set<ZRRequestFilter> filters;

	public void setMethods(Set<Method> methods) {
		this.methods = methods;
	}

	public void setFilters(Set<ZRRequestFilter> filters) {
		this.filters = filters;
	}

	public void init() {
		loop = new VThreadLoop();
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDestory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object execute(MethodInvoker invoker, HttpServletRequest request, String remoteIp) throws Throwable {

		return null;
	}

}
