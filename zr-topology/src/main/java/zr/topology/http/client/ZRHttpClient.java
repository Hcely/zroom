package zr.topology.http.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRContext;
import zr.monitor.ZRRequest;
import zr.monitor.ZRTopologyStack;
import zr.monitor.bean.result.ZRTopology;

@SuppressWarnings("deprecation")
public class ZRHttpClient extends CloseableHttpClient {
	protected final CloseableHttpClient client;

	public ZRHttpClient(CloseableHttpClient client) {
		this.client = client;
	}

	@Override
	protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		CloseableHttpResponse result = null;
		ZRTopologyStack stack = checkTopology();
		if (stack != null) {
			ZRTopology topology = stack.addTopology(request.getRequestLine().getUri(), "spring-http",
					System.currentTimeMillis());
			request.addHeader(ZRMonitorCenter.ZR_REQUEST_ID, stack.reqId());
			request.addHeader(ZRMonitorCenter.ZR_REQUEST_PREV_ID, topology.getPrevId());
			request.addHeader(ZRMonitorCenter.ZR_REQUEST_SILK_ID, topology.getSilkId());
		}
		try {
			result = client.execute(target, request, context);
			return result;
		} finally {
			if (stack != null) {
				byte resultStatus = checkResponse(result);
				stack.finishAndPopTopology(System.currentTimeMillis(), resultStatus);
				if (stack.isEmpty())
					ZRContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final byte checkResponse(CloseableHttpResponse result) {
		if (result == null)
			return ZRRequest.RESULT_ERROR;
		StatusLine status = result.getStatusLine();
		if (status == null)
			return ZRRequest.RESULT_OK;
		int code = status.getStatusCode();
		if (code < 400)
			return ZRRequest.RESULT_OK;
		return ZRRequest.RESULT_ERROR;
	}

	private static final ZRTopologyStack checkTopology() {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
		return stack.isEmpty() ? null : stack;
	}

	@Override
	public HttpParams getParams() {
		return client.getParams();
	}

	@Override
	public ClientConnectionManager getConnectionManager() {
		return client.getConnectionManager();
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

	public CloseableHttpClient getClient() {
		return client;
	}

}
