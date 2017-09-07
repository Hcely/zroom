package zr.monitor.statistic;

import v.common.unit.VThread;
import v.common.util.ProductQueue;

class ZRStatisticWorker extends VThread {
	protected final ZRStatisticCenter center;

	ZRStatisticWorker(ZRStatisticCenter center) {
		this.center = center;
	}

	@Override
	protected void run0() {
		long l;
		ZRStatisticTask task;
		final ProductQueue<ZRStatisticTask> queue = center.queue;
		final ZRStatisticHandler handler = center.taskHandler;
		while ((l = queue.consume()) != ProductQueue.IDX_DESTORY) {
			task = queue.item(l);
			try {
				handler.onHandle(task);
			} finally {
				queue.finishConsume(l);
			}
		}
	}

}
