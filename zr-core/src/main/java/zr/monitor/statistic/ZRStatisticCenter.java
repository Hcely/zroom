package zr.monitor.statistic;

import v.VObject;
import v.common.util.ProductQueue;

public class ZRStatisticCenter implements VObject {
	protected final ZRStatisticWorker[] workers;
	protected final ProductQueue<ZRStatisticTask> queue;
	protected final ZRStatisticHandler taskHandler;

	private boolean init;
	private boolean destory;

	public ZRStatisticCenter(int workerNum, int cacheSize, ZRStatisticHandler taskHandler) {
		workerNum = workerNum < 1 ? 1 : workerNum;
		this.workers = new ZRStatisticWorker[workerNum];
		this.queue = new ProductQueue<>(cacheSize, ZRStatisticTask.BUILDER);
		this.taskHandler = taskHandler;
		for (int i = 0; i < workerNum; ++i)
			workers[i] = new ZRStatisticWorker(this);
		init = destory = false;
	}

	public void init() {
		if (init)
			return;
		synchronized (this) {
			if (init)
				return;
			init = true;
			for (ZRStatisticWorker worker : workers)
				worker.start();
		}
	}

	public void putTask() {

	}

	@Override
	public void destory() {
		if (destory)
			return;
		synchronized (this) {
			if (destory || !init)
				return;
			destory = true;
		}
		queue.destory();
	}

	@Override
	public boolean isInit() {
		return init;
	}

	@Override
	public boolean isDestory() {
		return destory;
	}

	public void statistis() {

	}
}
