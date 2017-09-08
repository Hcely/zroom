package zr.monitor.statistic;

import v.common.unit.VThread;
import v.common.util.ProductQueue;
import zr.monitor.util.ZRApiCounts;

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
		ZRStatistic task;
		final ProductQueue<ZRStatistic> queue = center.queue;
		final ZRStatisticHandler handler = center.handler;
		while ((l = queue.consume()) != ProductQueue.IDX_DESTORY) {
			task = queue.item(l);
			try {
				count.add(task.id, task.methodName, task.version, task.take, task.respType);
				handler.onReqTask(task);
			} finally {
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
