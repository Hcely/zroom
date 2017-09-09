package zr.monitor;

public class ZRTopology implements Cloneable {
	private static final ZRTopology INSTANCE = new ZRTopology();

	static final ZRTopology create() {
		return INSTANCE.clone();
	}

	protected String reqId;
	protected String prevId;
	protected String silkId;
	protected String methodName;
	protected String version;
	protected long startTime;
	protected long take;
	protected int inc;

	private ZRTopology() {
		this.take = 0;
		this.inc = 0;
	}

	@Override
	protected ZRTopology clone() {
		try {
			return (ZRTopology) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	ZRTopology set(String reqId, String prevId, String silkId, String methodName, String version, long startTime) {
		this.reqId = reqId;
		this.prevId = prevId;
		this.silkId = silkId;
		this.methodName = methodName;
		this.version = version;
		this.startTime = startTime;
		return this;
	}

	void reset() {
		this.reqId = null;
		this.prevId = null;
		this.silkId = null;
		this.methodName = null;
		this.version = null;
		this.startTime = 0;
		this.take = 0;
		this.inc = 0;
	}

	public ZRTopology end() {
		this.take = System.currentTimeMillis() - startTime;
		return this;
	}

	public int incNum() {
		return ++inc;
	}

	public String getReqId() {
		return reqId;
	}

	public String getPrevId() {
		return prevId;
	}

	public String getSilkId() {
		return silkId;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getVersion() {
		return version;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getTake() {
		return take;
	}

}
