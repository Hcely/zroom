package zr.topology.dubbo;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

public class ZRDubboConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String reqId = RpcContext.getContext().getAttachment(ZRCenter.ZR_REQUEST_ID);
		Result result = null;
		try {
			result = invoker.invoke(invocation);
			return result;
		} finally {

		}
	}
	
	

}
