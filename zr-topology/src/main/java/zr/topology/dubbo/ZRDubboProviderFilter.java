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

public class ZRDubboProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		final ZRTopologyStack stack = checkTopology(invoker, invocation);
		final ZRTopology topology = stack == null ? null : stack.curTopology();
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
				ZRTopologyContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology(Invoker<?> invoker, Invocation invocation) {
		ZRTopologyStack stack = ZRTopologyContext.curTopologyStack();
		if (stack.isEmpty()) {
			RpcContext context = RpcContext.getContext();
			String reqId = context.getAttachment(ZRMonitorCenter.ZR_REQUEST_ID);
			if (reqId != null) {
				String prevId = context.getAttachment(ZRMonitorCenter.ZR_REQUEST_PREV_ID);
				String silkId = context.getAttachment(ZRMonitorCenter.ZR_REQUEST_SILK_ID);
				String methodName = ZRDubboUtil.getMethodName(invoker, invocation);
				return stack.start(reqId, prevId, silkId, methodName, "provider-dubbo", System.currentTimeMillis());
			}
		}
		return null;
	}

}
