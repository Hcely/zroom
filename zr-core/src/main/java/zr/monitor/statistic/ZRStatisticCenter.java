package zr.monitor.statistic;

import java.util.List;

import v.Initializable;
import v.common.unit.VStatusObject;
import v.common.unit.VThreadLoop;
import v.common.unit.VThreadLoop.VTimerTask;
import v.common.util.ProductQueue;
import zr.monitor.ZRRequest;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.info.ZRInfoMgr;

public class ZRStatisticCenter extends VStatusObject implements Initializable {
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
		this.handler = handler;
		for (int i = 0; i < workerNum; ++i)
			workers[i] = new ZRStatisticWorker(this);
	}

	@Override
	public void init() {
		if (!initing(this))
			return;
		try {
			for (ZRStatisticWorker worker : workers)
				worker.start();
			statusTask = loop.schedule(new ZRStatusTimerTask(this), 15000, 60000);
			countTask = loop.schedule(new ZRApiCountTimerTask(this), 60000, 60000);
		} finally {
			inited(this);
		}
	}

	public void putRequestTask(ZRRequest zreq, String reqId, List<ZRTopology> result, String logContent) {
		long i = queue.product();
		queue.item(i).set(zreq, reqId, result, logContent);
		queue.finishProduct(i);
	}

	public void putTopology(String reqId, List<ZRTopology> result) {
		long i = queue.product();
		queue.item(i).set(reqId, result);
		queue.finishProduct(i);
	}

	@Override
	public void destory() {
		if (!destorying(this))
			return;
		try {
			queue.destory();
			statusTask.cancel();
			countTask.cancel();
		} finally {
			destoryed(this);
		}
	}

}
