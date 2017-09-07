package zr.monitor.bean.info;

public class ZRDiskInfo {
	protected String name;
	protected String model;
	protected long size;
	protected long using;

	public ZRDiskInfo(String name, String model, long size, long using) {
		this.name = name;
		this.model = model;
		this.size = size;
		this.using = using;
	}

	public ZRDiskInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getUsing() {
		return using;
	}

	public void setUsing(long using) {
		this.using = using;
	}

}
