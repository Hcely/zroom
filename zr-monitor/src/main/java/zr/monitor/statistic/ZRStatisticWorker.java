package zr.monitor.statistic;

import v.common.unit.thread.VThread;
import v.common.util.ProductQueue;
import zr.monitor.bean.result.ZRRequestResult;

class ZRStatisticWorker extends VThread {
	protected final ZRStatisticCenter center;
	protected volatile ZRApiCounts cacheCount;
	protected volatile ZRApiCounts count;

	ZRStatisticWorker(ZRStatisticCenter center) {
		this.center = center;
		this.cacheCount = new ZRApiCounts();
		this.count = new ZRApiCounts();
	}

	@Override
	protected void run0() {
		long l;
		ZRStatistic e;
		final ProductQueue<ZRStatistic> queue = center.queue;
		final ZRStatisticHandler handler = center.handler;
		final String machineIp = center.infoMgr.getMachineIp();
		final String serverId = center.infoMgr.getServerId();
		final String serviceId = center.infoMgr.getServiceId();
		count.reset();
		while ((l = queue.consume()) != ProductQueue.IDX_DESTORY) {
			e = queue.item(l);
			try {
				switch (e.statisticType) {
				case ZRStatistic.TYPE_REQUEST: {
					ZRRequestResult result = e.requestResult;
					if (e.flags.size() > 0)
						result.setFlags(e.flags);
					count.add(e.methodId, result.getMethodName(), result.getVersion(), result.getTake(),
							result.getResultStatus());
					handler.onRequest(machineIp, serverId, serviceId, result);
					break;
				}
				case ZRStatistic.TYPE_REQUEST_TOPOLOPY: {
					ZRRequestResult result = e.requestResult;
					count.add(e.methodId, result.getMethodName(), result.getVersion(), result.getTake(),
							result.getResultStatus());
					handler.onRequest(machineIp, serverId, serviceId, result);
					handler.onTopology(machineIp, serverId, serviceId, e.topologyResult);
					break;
				}
				case ZRStatistic.TYPE_TOPOLOGY: {
					handler.onTopology(machineIp, serverId, serviceId, e.topologyResult);
					break;
				}
				}
			} finally {
				e.reset();
				queue.finishConsume(l);
			}
		}
	}

	public ZRApiCounts swapCount() {
		ZRApiCounts tmp = this.count;
		this.count = cacheCount.reset();
		cacheCount = tmp;
		return tmp;
	}

}
