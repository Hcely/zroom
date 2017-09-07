package zr.monitor;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.PostConstruct;

import v.Initializable;
import v.common.unit.VThreadLoop;
import zr.monitor.cluster.ZRClusterServer;
import zr.monitor.info.ZRInfoMgr;
import zr.monitor.method.ZRMethodMgr;
import zr.monitor.statistic.ZRStatisticCenter;
import zr.monitor.status.ZRMonitorStatusMgr;

public class ZRMonitorCenter implements Initializable {
	protected static final String IP_HEADER = "X-real-ip";
	protected static final String SPRING_CGLIB = "EnhancerBySpringCGLIB";
	protected VThreadLoop loop;
	protected ZRInfoMgr infoMgr;
	protected ZRMethodMgr methodMgr;
	protected ZRMonitorStatusMgr statusMgr;
	protected ZRStatisticCenter statisticCenter;
	protected ZRClusterServer clusterServer;
	protected Set<Method> methods;
	protected Set<ZRRequestFilter> filters;

	@PostConstruct
	public void init() {

	}

	public Object execute(Method method) throws Throwable {

		return null;
	}

}
