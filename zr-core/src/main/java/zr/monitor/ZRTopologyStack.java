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
	private ZRTopology firstTopology;
	private ZRTopology curTopology;

	public ZRTopologyStack() {
		stacks = new ZRTopology[32];
		capacity = stacks.length;
		len = 0;
	}

	public ZRTopologyStack start(final String reqId, final String prevId, final String methodName, final String version,
			final long startTime, final int idx) {
		this.reqId = reqId;
		this.topologys = new LinkedList<>();
		String silkId = prevId == null ? ZRMonitorUtil.buildSilkId(methodName.hashCode(), idx)
				: ZRMonitorUtil.buildSilkId(prevId.hashCode(), idx);
		this.firstTopology = addStack(prevId, methodName, version, silkId, idx, startTime);
		return this;
	}

	public ZRTopology addTopology(final String methodName, final String version, final long startTime) {
		String prevId = curTopology.getPrevId();
		int idx = curTopology.incNum();
		String silkId = ZRMonitorUtil.buildSilkId(prevId.hashCode(), idx);
		return addStack(prevId, methodName, version, silkId, idx, startTime);
	}

	public ZRTopology popAndFinishTopology(final long endTime, final byte resultStatus) {
		ZRTopology topology = curTopology;
		popAndNext();
		return topology.finish(endTime, resultStatus);
	}

	public LinkedList<ZRTopology> finishAndGetResult() {
		LinkedList<ZRTopology> list = topologys;
		reqId = null;
		topologys = null;
		firstTopology = null;
		return list;
	}

	public String reqId() {
		return reqId;
	}

	public ZRTopology firstTopology() {
		return firstTopology;
	}

	public ZRTopology curTopology() {
		return curTopology;
	}

	public boolean isEmpty() {
		return len == 0;
	}

	private final ZRTopology addStack(String prevId, String methodName, String version, String silkId, int idx,
			long startTime) {
		checkCapacity();
		ZRTopology topology = curTopology = new ZRTopology(prevId, methodName, version, silkId, idx, startTime);
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
