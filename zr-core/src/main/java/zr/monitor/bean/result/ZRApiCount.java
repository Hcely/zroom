package zr.monitor.bean.result;

import zr.monitor.ZRRequest;

public class ZRApiCount implements Cloneable {

	protected final String methodName;
	protected final String version;

	protected int sum;
	protected long takeTime;
	protected int count100ms;
	protected int count250ms;
	protected int count500ms;
	protected int count1000ms;
	protected int count2000ms;
	protected int countSlow;

	protected int countOk;
	protected int countBad;
	protected int countError;

	public ZRApiCount(String methodName, String version) {
		this.methodName = methodName;
		this.version = version;
		reset();
	}

	@Override
	public ZRApiCount clone() {
		try {
			return (ZRApiCount) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(ZRApiCount count) {
		takeTime += count.takeTime;
		this.sum += count.sum;
		count100ms += count.count100ms;
		count250ms += count.count250ms;
		count500ms += count.count500ms;
		count1000ms += count.count1000ms;
		count2000ms += count.count2000ms;
		countSlow += count.countSlow;

		countOk += count.countOk;
		countBad += count.countBad;
		countError += count.countError;
	}

	public void add(long take, byte resultStatus) {
		takeTime += take;
		++sum;
		if (takeTime < 101)
			++count100ms;
		else if (takeTime < 251)
			++count250ms;
		else if (takeTime < 501)
			++count500ms;
		else if (takeTime < 1001)
			++count1000ms;
		else if (takeTime < 2001)
			++count2000ms;
		else
			++countSlow;

		if (resultStatus == ZRRequest.RESULT_OK)
			++countOk;
		else if (resultStatus == ZRRequest.RESULT_BAD)
			++countBad;
		else
			++countError;
	}

	public void reset() {
		sum = 0;
		takeTime = 0;
		count100ms = 0;
		count250ms = 0;
		count500ms = 0;
		count1000ms = 0;
		count2000ms = 0;
		countSlow = 0;
		countOk = 0;
		countBad = 0;
		countError = 0;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public void setCount100ms(int count100ms) {
		this.count100ms = count100ms;
	}

	public void setCount250ms(int count250ms) {
		this.count250ms = count250ms;
	}

	public void setCount500ms(int count500ms) {
		this.count500ms = count500ms;
	}

	public void setCount1000ms(int count1000ms) {
		this.count1000ms = count1000ms;
	}

	public void setCount2000ms(int count2000ms) {
		this.count2000ms = count2000ms;
	}

	public void setCountSlow(int countSlow) {
		this.countSlow = countSlow;
	}

	public void setCountOk(int countOk) {
		this.countOk = countOk;
	}

	public void setCountBad(int countBad) {
		this.countBad = countBad;
	}

	public void setCountError(int countError) {
		this.countError = countError;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getVersion() {
		return version;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public int getCount100ms() {
		return count100ms;
	}

	public int getCount250ms() {
		return count250ms;
	}

	public int getCount500ms() {
		return count500ms;
	}

	public int getCount1000ms() {
		return count1000ms;
	}

	public int getCount2000ms() {
		return count2000ms;
	}

	public int getCountSlow() {
		return countSlow;
	}

	public int getCountOk() {
		return countOk;
	}

	public int getCountBad() {
		return countBad;
	}

	public int getCountError() {
		return countError;
	}
}
