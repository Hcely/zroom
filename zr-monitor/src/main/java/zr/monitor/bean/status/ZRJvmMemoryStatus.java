package zr.monitor.bean.status;

public class ZRJvmMemoryStatus {
	protected String name;
	protected String type;
	protected long init;
	protected long using;
	protected long total;
	protected long max;

	public void set(long init, long using, long total, long max) {
		this.init = init;
		this.using = using;
		this.total = total;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getInit() {
		return init;
	}

	public void setInit(long init) {
		this.init = init;
	}

	public long getUsing() {
		return using;
	}

	public void setUsing(long using) {
		this.using = using;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

}
