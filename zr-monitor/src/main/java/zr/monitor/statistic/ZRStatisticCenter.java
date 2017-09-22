package zr.monitor.statistic;

import v.common.unit.VSimpleStatusObject;
import v.common.unit.thread.VThreadLoop;
import v.common.unit.thread.VThreadLoop.VTimerTask;
import v.common.util.ProductQueue;
import zr.monitor.info.ZRInfoMgr;

public class ZRStatisticCenter extends VSimpleStatusObject {
	protected final ZRInfoMgr infoMgr;
	protected final VThreadLoop loop;
	protected final ZRStatisticWorker[] workers;
	protected final ProductQueue<ZRStatistic> queue;
	protected final ZRStatisticHandler handler;

	protected VTimerTask statusTask;
	protected VTimerTask countTask;

	public ZRStatisticCenter(ZRInfoMgr infoMgr, VThreadLoop loop, int workerNum, int cacheSize,
			ZRStatisticHandler handler) {
		workerNum = workerNum < 1 ? 1 : workerNum;
		this.infoMgr = infoMgr;
		this.loop = loop;
		this.workers = new ZRStatisticWorker[workerNum];
		this.queue = new ProductQueue<>(cacheSize, ZRStatistic.BUILDER);
		this.handler = handler == null ? ZRStatisticHandler.DEF : handler;
		for (int i = 0; i < workerNum; ++i)
			workers[i] = new ZRStatisticWorker(this);
	}

	@Override
	protected final void _init0() {
		for (ZRStatisticWorker worker : workers)
			worker.start();
		statusTask = loop.schedule(new ZRStatusTimerTask(this), 15000, 60000);
		countTask = loop.schedule(new ZRApiCountTimerTask(this), 60000, 60000);
	}

	public ZRStatistic product() {
		long i = queue.product();
		ZRStatistic e = queue.item(i);
		e.idx = i;
		return e;
	}

	public void finishProduct(ZRStatistic e) {
		queue.finishProduct(e.idx);
	}

	@Override
	protected void _destory0() {
		queue.destory();
		statusTask.cancel();
		countTask.cancel();
	}

}
