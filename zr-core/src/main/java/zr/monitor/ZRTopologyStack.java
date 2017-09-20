package zr.monitor;

import java.util.LinkedList;

import zr.monitor.bean.result.ZRTopology;

public class ZRTopologyStack {
	private final ZRTopology defHeader;
	private int len;
	private int capacity;
	private ZRTopology[] stacks;

	private String reqId;
	private ZRTopology header;

	private LinkedList<ZRTopology> topologys;
	private ZRTopology curTopology;

	public ZRTopologyStack() {
		stacks = new ZRTopology[32];
		capacity = stacks.length;
		len = 0;
		defHeader = new ZRTopology(null, "root");
	}

	public ZRTopologyStack start(final String reqId, final String prevId, final String silkId, final String methodName,
			final String version, final long startTime) {
		return start(reqId, prevId, silkId, methodName, version, startTime, false);
	}

	public ZRTopologyStack start(final String reqId, final String prevId, final String silkId, final String methodName,
			final String version, final long startTime, boolean rootNode) {
		this.topologys = new LinkedList<>();
		if (reqId.equals(this.reqId)) {
			curTopology = header;
			addTopology(methodName, version, startTime);
			return this;
		} else {
			this.reqId = reqId;
			if (prevId == null) {
				curTopology = header = defHeader.resetNum();
				addTopology(methodName, version, startTime);
				return this;
			} else {
				curTopology = header = addStack(prevId, silkId, methodName, version, startTime);
				if (rootNode)
					topologys.add(curTopology);
				return this;
			}
		}
	}

	public ZRTopology getHeader() {
		return header;
	}

	public ZRTopology addTopology(final String methodName, final String version, final long startTime) {
		return addTopology(methodName, version, startTime, true);
	}

	public ZRTopology addTopology(final String methodName, final String version, final long startTime,
			final boolean record) {
		ZRTopology topology = addStack(curTopology.getSilkId(), curTopology.nextSilkId(), methodName, version,
				startTime);
		if (record)
			topologys.add(topology);
		return topology;
	}

	public ZRTopology finishAndPopTopology(final ZRTopology topology, final long endTime, final byte resultStatus) {
		if (topology == curTopology)
			popTopology();
		else
			for (ZRTopology badTopology = popTopology(); topology != badTopology; badTopology = popTopology())
				badTopology.finish(endTime, resultStatus);
		return topology.finish(endTime, resultStatus);
	}

	public LinkedList<ZRTopology> finishAndGetResult() {
		LinkedList<ZRTopology> list = topologys;
		topologys = null;
		return list;
	}

	public String reqId() {
		return reqId;
	}

	public ZRTopology curTopology() {
		return curTopology;
	}

	public boolean isEmpty() {
		return len == 0;
	}

	private final ZRTopology addStack(String prevId, String silkId, String methodName, String version, long startTime) {
		checkCapacity();
		ZRTopology topology = stacks[len++] = curTopology = new ZRTopology(prevId, silkId, methodName, version,
				startTime);
		return topology;
	}

	private final ZRTopology popTopology() {
		ZRTopology tmp = curTopology;
		stacks[--len] = null;
		curTopology = len == 0 ? null : stacks[len - 1];
		return tmp;
	}

	private void checkCapacity() {
		if (capacity > len)
			return;
		ZRTopology[] tmp = new ZRTopology[capacity + 32];
		System.arraycopy(stacks, 0, tmp, 0, capacity);
		stacks = tmp;
		capacity = stacks.length;
	}

}
