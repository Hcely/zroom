package zr.monitor.statistic;

import java.util.ArrayList;
import java.util.Enumeration;

class ZRApiCountTimerTask implements Runnable {
	protected final ZRStatisticCenter center;
	protected final ZRApiCounts count;
	protected long lastTime;
	protected final ArrayList<ZRApiCount> list;

	public ZRApiCountTimerTask(ZRStatisticCenter center) {
		this.center = center;
		this.count = new ZRApiCounts();
		this.lastTime = System.currentTimeMillis();
		this.list = new ArrayList<>(64);
	}

	@Override
	public void run() {
		count.reset();
		for (ZRStatisticWorker e : center.workers)
			count.add(e.swapCount());
		long curTime = System.currentTimeMillis();
		list.clear();
		for (Enumeration<ZRApiCount> it = count.enumeration(); it.hasMoreElements();)
			list.add(it.nextElement());
		center.handler.onApiCount(center.infoMgr.getMachineIp(), center.infoMgr.getServerId(),
				center.infoMgr.getServiceId(), lastTime, curTime, list);
		lastTime = curTime;
	}

}
