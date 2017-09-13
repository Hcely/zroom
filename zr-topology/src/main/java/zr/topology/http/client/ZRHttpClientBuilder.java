package zr.topology.http.client;

import org.apache.http.impl.client.HttpClientBuilder;

public class ZRHttpClientBuilder extends HttpClientBuilder {
	@Override
	public ZRHttpClient build() {
		return new ZRHttpClient(super.build());
	}

}
