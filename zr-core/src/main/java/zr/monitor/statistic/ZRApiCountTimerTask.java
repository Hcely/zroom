package zr.monitor.statistic;

import zr.monitor.util.ZRApiCounts;

class ZRApiCountTimerTask implements Runnable {
	protected final ZRStatisticCenter center;
	protected final ZRApiCounts count;

	public ZRApiCountTimerTask(ZRStatisticCenter center) {
		this.center = center;
		this.count = new ZRApiCounts();
	}

	@Override
	public void run() {
		count.reset();
		for (ZRStatisticWorker e : center.workers)
			count.add(e.swapCount());
		center.handler.onApiCount(center.machineIp, center.serverId, center.serviceId, count);
	}

}
