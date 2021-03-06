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
import zr.monitor.ZRequest;
import zr.monitor.bean.result.ZRTopology;
import zr.monitor.topology.ZRTopologyContext;
import zr.monitor.topology.ZRTopologyStack;

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
		final ZRTopologyStack stack = checkTopology();
		ZRTopology topology = null;
		if (stack != null) {
			topology = stack.addTopology(url.getPath(), "spring-http", System.currentTimeMillis());
			requestCallback = new RCallback(requestCallback, stack.reqId(), topology);
		}
		try {
			result = super.doExecute(url, method, requestCallback, responseExtractor);
			return result;
		} finally {
			if (stack != null) {
				stack.finishAndPopTopology(topology, System.currentTimeMillis(), ZRequest.RESULT_OK);
				if (stack.isEmpty())
					ZRTopologyContext.putTopology(stack.reqId(), stack.finishAndGetResult());
			}
		}
	}

	private static final ZRTopologyStack checkTopology() {
		ZRTopologyStack stack = ZRTopologyContext.curTopologyStack();
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
