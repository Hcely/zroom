package zr.monitor.statistic;

import java.util.ArrayList;
import java.util.Enumeration;

import zr.monitor.bean.result.ZRApiCount;
import zr.monitor.bean.result.ZRApiCountResult;

class ZRApiCountTimerTask implements Runnable {
	protected final ZRStatisticCenter center;
	protected final ZRApiCounts count;
	protected long lastTime;
	protected final ArrayList<ZRApiCount> list;
	protected final ZRApiCountResult result;

	public ZRApiCountTimerTask(ZRStatisticCenter center) {
		this.center = center;
		this.count = new ZRApiCounts();
		this.lastTime = System.currentTimeMillis();
		this.list = new ArrayList<>(64);
		this.result = new ZRApiCountResult();
		result.setApiCounts(list);
	}

	@Override
	public void run() {
		count.reset();
		for (ZRStatisticWorker e : center.workers)
			count.add(e.swapCount());
		long curTime = System.currentTimeMillis();
		list.clear();
		for (Enumeration<ZRApiCount> it = count.enumeration(); it.hasMoreElements();) {
			ZRApiCount count = it.nextElement();
			if (count.getSum() > 0)
				list.add(count);
		}
		if (list.size() > 0) {
			result.setTime(lastTime, curTime);
			center.handler.onApiCount(center.infoMgr.getMachineIp(), center.infoMgr.getServerId(),
					center.infoMgr.getServiceId(), result);
		}
		lastTime = curTime;
	}

}
