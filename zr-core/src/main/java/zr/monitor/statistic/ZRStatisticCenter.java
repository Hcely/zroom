package zr.monitor.statistic;

import v.VObject;
import v.common.unit.VThreadLoop;
import v.common.unit.VThreadLoop.VTimerTask;
import v.common.util.ProductQueue;
import zr.monitor.ZRRequest;
import zr.monitor.ZRTopology;
import zr.monitor.info.ZRInfoMgr;

public class ZRStatisticCenter implements VObject {
	protected final ZRInfoMgr infoMgr;
	protected final VThreadLoop loop;
	protected final ZRStatisticWorker[] workers;
	protected final ProductQueue<ZRStatistic> queue;
	protected final ZRStatisticHandler handler;

	protected VTimerTask statusTask;
	protected VTimerTask countTask;

	private boolean init;
	private boolean destory;

	public ZRStatisticCenter(ZRInfoMgr infoMgr, VThreadLoop loop, int workerNum, int cacheSize,
			ZRStatisticHandler taskHandler) {
		workerNum = workerNum < 1 ? 1 : workerNum;
		this.infoMgr = infoMgr;
		this.loop = loop;
		this.workers = new ZRStatisticWorker[workerNum];
		this.queue = new ProductQueue<>(cacheSize, ZRStatistic.BUILDER);
		this.handler = taskHandler;
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
			statusTask = loop.schedule(new ZRStatusTimerTask(this), 15000, 60000);
		}
	}

	public void putRequestTask(ZRRequest zreq, ZRTopology topology, String logContent) {
		long i = queue.product();
		queue.item(i).set(zreq, topology, logContent);
		queue.finishProduct(i);
	}

	public void putTopology(ZRTopology topology, byte resultType) {
		long i = queue.product();
		queue.item(i).set(topology, resultType);
		queue.finishProduct(i);
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
		statusTask.cancel();
	}

	@Override
	public boolean isInit() {
		return init;
	}

	@Override
	public boolean isDestory() {
		return destory;
	}

}
