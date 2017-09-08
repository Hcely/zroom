package zr.monitor.statistic;

import v.VObject;
import v.common.unit.VThreadLoop;
import v.common.unit.VThreadLoop.VTimerTask;
import v.common.util.ProductQueue;

public class ZRStatisticCenter implements VObject {
	protected final String machineIp;
	protected final String serverId;
	protected final String serviceId;
	protected final VThreadLoop loop;
	protected final ZRStatisticWorker[] workers;
	protected final ProductQueue<ZRStatistic> queue;
	protected final ZRStatisticHandler handler;

	protected VTimerTask statusTask;
	protected VTimerTask countTask;
	protected volatile boolean serverStatus;
	protected volatile boolean machineStatus;

	private boolean init;
	private boolean destory;

	public ZRStatisticCenter(String machineIp, String serverId, String serviceId, VThreadLoop loop, int workerNum,
			int cacheSize, ZRStatisticHandler taskHandler) {
		workerNum = workerNum < 1 ? 1 : workerNum;
		this.machineIp = machineIp;
		this.serverId = serverId;
		this.serviceId = serviceId;
		this.loop = loop;
		this.workers = new ZRStatisticWorker[workerNum];
		this.queue = new ProductQueue<>(cacheSize, ZRStatistic.builder(machineIp, serverId, serviceId));
		this.handler = taskHandler;
		for (int i = 0; i < workerNum; ++i)
			workers[i] = new ZRStatisticWorker(this);
		init = destory = false;
		machineStatus = serverStatus = false;
	}

	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}

	public void setMachineStatus(boolean machineStatus) {
		this.machineStatus = machineStatus;
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
