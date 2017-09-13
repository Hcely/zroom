package zr.kafka;

import java.util.Properties;

import v.ObjBuilder;
import v.VObject;

public class ZRKafkaProvider implements VObject {
	public static final class Builder implements ObjBuilder<ZRKafkaProvider> {
		public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
		public static final String KEY_SERIALIZER = "key.serializer";
		public static final String VALUE_SERIALIZER = "value.serializer";
		public static final String ACKS = "acks";
		public static final String BUFFER_MEMORY = "buffer.memory";
		public static final String COMPRESSION_TYPE = "compression.type";
		public static final String RETRIES = "retries";
		public static final String SSL_KEY_PASSWORD = "ssl.key.password";
		public static final String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
		public static final String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
		public static final String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
		public static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
		public static final String BATCH_SIZE = "batch.size";
		public static final String CLIENT_ID = "client.id";
		public static final String CONNECTIONS_MAX_IDLE_MS = "connections.max.idle.ms";
		public static final String LINGER_MS = "linger.ms";
		public static final String MAX_BLOCK_MS = "max.block.ms";
		public static final String MAX_REQUEST_SIZE = "max.request.size";
		public static final String PARTITIONER_CLASS = "partitioner.class";
		public static final String RECEIVE_BUFFER_BYTES = "receive.buffer.bytes";
		public static final String REQUEST_TIMEOUT_MS = "request.timeout.ms";
		public static final String SASL_JAAS_CONFIG = "sasl.jaas.config";
		public static final String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
		public static final String SASL_MECHANISM = "sasl.mechanism";
		public static final String SECURITY_PROTOCOL = "security.protocol";
		public static final String SEND_BUFFER_BYTES = "send.buffer.bytes";
		public static final String SSL_ENABLED_PROTOCOLS = "ssl.enabled.protocols";
		public static final String SSL_KEYSTORE_TYPE = "ssl.keystore.type";
		public static final String SSL_PROTOCOL = "ssl.protocol";
		public static final String SSL_PROVIDER = "ssl.provider";
		public static final String SSL_TRUSTSTORE_TYPE = "ssl.truststore.type";
		public static final String ENABLE_IDEMPOTENCE = "enable.idempotence";
		public static final String INTERCEPTOR_CLASSES = "interceptor.classes";
		public static final String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max.in.flight.requests.per.connection";
		public static final String METADATA_MAX_AGE_MS = "metadata.max.age.ms";
		public static final String METRIC_REPORTERS = "metric.reporters";
		public static final String METRICS_NUM_SAMPLES = "metrics.num.samples";
		public static final String METRICS_RECORDING_LEVEL = "metrics.recording.level";
		public static final String METRICS_SAMPLE_WINDOW_MS = "metrics.sample.window.ms";
		public static final String RECONNECT_BACKOFF_MAX_MS = "reconnect.backoff.max.ms";
		public static final String RECONNECT_BACKOFF_MS = "reconnect.backoff.ms";
		public static final String RETRY_BACKOFF_MS = "retry.backoff.ms";
		public static final String SASL_KERBEROS_KINIT_CMD = "sasl.kerberos.kinit.cmd";
		public static final String SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN = "sasl.kerberos.min.time.before.relogin";
		public static final String SASL_KERBEROS_TICKET_RENEW_JITTER = "sasl.kerberos.ticket.renew.jitter";
		public static final String SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR = "sasl.kerberos.ticket.renew.window.factor";
		public static final String SSL_CIPHER_SUITES = "ssl.cipher.suites";
		public static final String SSL_ENDPOINT_IDENTIFICATION_ALGORITHM = "ssl.endpoint.identification.algorithm";
		public static final String SSL_KEYMANAGER_ALGORITHM = "ssl.keymanager.algorithm";
		public static final String SSL_SECURE_RANDOM_IMPLEMENTATION = "ssl.secure.random.implementation";
		public static final String SSL_TRUSTMANAGER_ALGORITHM = "ssl.trustmanager.algorithm";
		public static final String TRANSACTION_TIMEOUT_MS = "transaction.timeout.ms";
		public static final String TRANSACTIONAL_ID = "transactional.id";

		protected final Properties properties = new Properties();

		public void setServers(String servers) {

		}

		@Override
		public ZRKafkaProvider build() {
			return null;
		}

		@Override
		public Class<ZRKafkaProvider> getType() {
			return ZRKafkaProvider.class;
		}
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDestory() {
		// TODO Auto-generated method stub
		return false;
	}

}
