package zr.monitor.bean.result;

import java.util.List;

public class ZRApiCountResult {
	protected long startTime;
	protected long endTime;
	protected List<ZRApiCount> apiCounts;

	public void setTime(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public List<ZRApiCount> getApiCounts() {
		return apiCounts;
	}

	public void setApiCounts(List<ZRApiCount> apiCounts) {
		this.apiCounts = apiCounts;
	}

}
