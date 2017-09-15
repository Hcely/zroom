package zr.kafka;

public class ProviderParamKey {
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String KEY_SERIALIZER = "key.serializer";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String VALUE_SERIALIZER = "value.serializer";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String ACKS = "acks";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String BUFFER_MEMORY = "buffer.memory";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String COMPRESSION_TYPE = "compression.type";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String RETRIES = "retries";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_KEY_PASSWORD = "ssl.key.password";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String BATCH_SIZE = "batch.size";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String CLIENT_ID = "client.id";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String CONNECTIONS_MAX_IDLE_MS = "connections.max.idle.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String LINGER_MS = "linger.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String MAX_BLOCK_MS = "max.block.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String MAX_REQUEST_SIZE = "max.request.size";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String PARTITIONER_CLASS = "partitioner.class";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String RECEIVE_BUFFER_BYTES = "receive.buffer.bytes";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String REQUEST_TIMEOUT_MS = "request.timeout.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_JAAS_CONFIG = "sasl.jaas.config";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_MECHANISM = "sasl.mechanism";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SECURITY_PROTOCOL = "security.protocol";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SEND_BUFFER_BYTES = "send.buffer.bytes";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_ENABLED_PROTOCOLS = "ssl.enabled.protocols";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_KEYSTORE_TYPE = "ssl.keystore.type";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_PROTOCOL = "ssl.protocol";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_PROVIDER = "ssl.provider";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_TRUSTSTORE_TYPE = "ssl.truststore.type";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String ENABLE_IDEMPOTENCE = "enable.idempotence";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String INTERCEPTOR_CLASSES = "interceptor.classes";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max.in.flight.requests.per.connection";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String METADATA_MAX_AGE_MS = "metadata.max.age.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String METRIC_REPORTERS = "metric.reporters";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String METRICS_NUM_SAMPLES = "metrics.num.samples";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String METRICS_RECORDING_LEVEL = "metrics.recording.level";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String METRICS_SAMPLE_WINDOW_MS = "metrics.sample.window.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String RECONNECT_BACKOFF_MAX_MS = "reconnect.backoff.max.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String RECONNECT_BACKOFF_MS = "reconnect.backoff.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String RETRY_BACKOFF_MS = "retry.backoff.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_KERBEROS_KINIT_CMD = "sasl.kerberos.kinit.cmd";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN = "sasl.kerberos.min.time.before.relogin";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_KERBEROS_TICKET_RENEW_JITTER = "sasl.kerberos.ticket.renew.jitter";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR = "sasl.kerberos.ticket.renew.window.factor";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_CIPHER_SUITES = "ssl.cipher.suites";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_ENDPOINT_IDENTIFICATION_ALGORITHM = "ssl.endpoint.identification.algorithm";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_KEYMANAGER_ALGORITHM = "ssl.keymanager.algorithm";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_SECURE_RANDOM_IMPLEMENTATION = "ssl.secure.random.implementation";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String SSL_TRUSTMANAGER_ALGORITHM = "ssl.trustmanager.algorithm";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String TRANSACTION_TIMEOUT_MS = "transaction.timeout.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String TRANSACTIONAL_ID = "transactional.id";
}
