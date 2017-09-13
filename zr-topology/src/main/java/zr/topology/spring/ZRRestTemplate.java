package zr.topology.spring;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import zr.monitor.ZRMonitorCenter;
import zr.monitor.ZRContext;
import zr.monitor.ZRRequest;
import zr.monitor.ZRTopologyStack;
import zr.monitor.bean.result.ZRTopology;

public class ZRRestTemplate extends RestTemplate {

	public ZRRestTemplate() {
		super();
	}

	public ZRRestTemplate(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}

	public ZRRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	@Override
	protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor) throws RestClientException {
		T result = null;
		ZRTopologyStack stack = checkTopology();
		if (stack != null) {
			ZRTopology topology = stack.addTopology(url.getPath(), "spring-http", System.currentTimeMillis());
			requestCallback = new RCallback(requestCallback, stack.reqId(), topology);
		}
		try {
			result = super.doExecute(url, method, requestCallback, responseExtractor);
			return result;
		} finally {
			if (stack != null) {
				stack.finishAndPopTopology(System.currentTimeMillis(), ZRRequest.RESULT_OK);
				if (stack.isEmpty())
					ZRContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology() {
		ZRTopologyStack stack = ZRContext.curTopologyStack();
		return stack.isEmpty() ? null : stack;
	}

	private static final class RCallback implements RequestCallback {
		final RequestCallback requestCallback;
		final String reqId;
		final ZRTopology topology;

		public RCallback(RequestCallback requestCallback, String reqId, ZRTopology topology) {
			this.requestCallback = requestCallback;
			this.reqId = reqId;
			this.topology = topology;
		}

		@Override
		public void doWithRequest(ClientHttpRequest request) throws IOException {
			request.getHeaders().add(ZRMonitorCenter.ZR_REQUEST_ID, reqId);
			request.getHeaders().add(ZRMonitorCenter.ZR_REQUEST_PREV_ID, topology.getPrevId());
			request.getHeaders().add(ZRMonitorCenter.ZR_REQUEST_SILK_ID, topology.getSilkId());
			requestCallback.doWithRequest(request);
		}

	}

}
