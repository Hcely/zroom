package zr.topology.dubbo;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRContext;
import zr.monitor.ZRRequest;
import zr.monitor.ZRTopologyStack;
import zr.monitor.bean.result.ZRTopology;

public class ZRDubboConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		ZRTopologyStack stack = checkTopology();
		if (stack != null) {
			String methodName = ZRDubboUtil.getMethodName(invoker, invocation);
			ZRTopology topology = stack.addTopology(methodName, "consumer-dubbo", System.currentTimeMillis());
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
				byte resultStatus = ZRRequest.RESULT_OK;
				if (result == null || result.hasException())
					resultStatus = ZRRequest.RESULT_ERROR;
				stack.finishAndPopTopology(System.currentTimeMillis(), resultStatus);
				if (stack.isEmpty())
					ZRContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology() {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
		return stack.isEmpty() ? null : stack;
	}

}
