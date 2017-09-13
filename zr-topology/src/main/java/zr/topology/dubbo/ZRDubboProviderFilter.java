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

public class ZRDubboProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		ZRTopologyStack stack = checkTopology(invoker, invocation);
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
				ZRContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology(Invoker<?> invoker, Invocation invocation) {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
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
