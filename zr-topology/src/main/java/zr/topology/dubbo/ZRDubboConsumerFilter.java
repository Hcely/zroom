package zr.topology.dubbo;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRequest;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.topology.ZRTopologyContext;
import zr.monitor.topology.ZRTopologyStack;

public class ZRDubboConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		final ZRTopologyStack stack = checkTopology();
		ZRTopology topology = null;
		if (stack != null) {
			String methodName = ZRDubboUtil.getMethodName(invoker, invocation);
			topology = stack.addTopology(methodName, "consumer-dubbo", System.currentTimeMillis());
			RpcContext context = RpcContext.getContext();
			context.setAttachment(ZRMonitorCenter.ZR_REQUEST_ID, stack.reqId());
			context.setAttachment(ZRMonitorCenter.ZR_REQUEST_PREV_ID, topology.getPrevId());
			context.setAttachment(ZRMonitorCenter.ZR_REQUEST_SILK_ID, topology.getSilkId());
		}
		Result result = null;
		try {
			result = invoker.invoke(invocation);
			return result;
		} finally {
			if (stack != null) {
				byte resultStatus = ZRequest.RESULT_OK;
				if (result == null || result.hasException())
					resultStatus = ZRequest.RESULT_ERROR;
				stack.finishAndPopTopology(topology, System.currentTimeMillis(), resultStatus);
				if (stack.isEmpty())
					ZRTopologyContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology() {
		ZRTopologyStack stack = ZRTopologyContext.curTopologyStack();
		return stack.isEmpty() ? null : stack;
	}

}
