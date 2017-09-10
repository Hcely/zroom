package zr.monitor;

import java.util.LinkedList;

import zr.monitor.bean.result.ZRTopology;
import zr.monitor.util.ZRMonitorUtil;

public class ZRTopologyStack {
	private int len;
	private int capacity;
	private ZRTopology[] stacks;

	private String reqId;
	private LinkedList<ZRTopology> topologys;
	private ZRTopology curTopology;

	public ZRTopologyStack() {
		stacks = new ZRTopology[32];
		capacity = stacks.length;
		len = 0;
	}

	public ZRTopologyStack start(final String reqId, final String prevId, final String silkId, final String methodName,
			final String version, final long startTime) {
		this.reqId = reqId;
		this.topologys = new LinkedList<>();
		addStack(prevId, buildSilkId(silkId, methodName), methodName, version, startTime);
		return this;
	}

	private static final String buildSilkId(final String silkId, final String methodName) {
		return silkId == null ? ZRMonitorUtil.buildSilkId(methodName.hashCode(), 0) : silkId;
	}

	public ZRTopology addTopology(final String methodName, final String version, final long startTime) {
		return addStack(curTopology.getSilkId(), curTopology.nextSilkId(), methodName, version, startTime);
	}

	public ZRTopology finishAndPopTopology(final long endTime, final byte resultStatus) {
		ZRTopology topology = curTopology;
		popAndNext();
		return topology.finish(endTime, resultStatus);
	}

	public LinkedList<ZRTopology> finishAndGetResult() {
		LinkedList<ZRTopology> list = topologys;
		reqId = null;
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
		ZRTopology topology = curTopology = new ZRTopology(prevId, silkId, methodName, version, startTime);
		topologys.add(topology);
		stacks[len++] = topology;
		return topology;
	}

	private final void popAndNext() {
		stacks[--len] = null;
		curTopology = len == 0 ? null : stacks[len - 1];
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
