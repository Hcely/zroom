package zr.kafka;

public class ConsumerParamKey {
	/**
	 * @type list
	 * @description A list of host/port pairs to use for establishing the
	 *              initial connection to the Kafka cluster. The client will
	 *              make use of all servers irrespective of which servers are
	 *              specified here for bootstrapping—this list only impacts the
	 *              initial hosts used to discover the full set of servers. This
	 *              list should be in the form host1:port1,host2:port2,....
	 *              Since these servers are just used for the initial connection
	 *              to discover the full cluster membership (which may change
	 *              dynamically), this list need not contain the full set of
	 *              servers (you may want more than one, though, in case a
	 *              server is down).
	 * @default
	 * @importance high
	 */
	public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
	/**
	 * @type class
	 * @description Deserializer class for key that implements the Deserializer
	 *              interface.example:org.apache.kafka.common.serialization.StringDeserializer
	 * @default
	 * @importance high
	 */
	public static final String KEY_DESERIALIZER = "key.deserializer";
	/**
	 * @type class
	 * @description Deserializer class for value that implements the
	 *              Deserializer
	 *              interface.example:org.apache.kafka.common.serialization.StringDeserializer
	 * @default
	 * @importance high
	 */
	public static final String VALUE_DESERIALIZER = "value.deserializer";
	/**
	 * @type int
	 * @description The minimum amount of data the server should return for a
	 *              fetch request. If insufficient data is available the request
	 *              will wait for that much data to accumulate before answering
	 *              the request. The default setting of 1 byte means that fetch
	 *              requests are answered as soon as a single byte of data is
	 *              available or the fetch request times out waiting for data to
	 *              arrive. Setting this to something greater than 1 will cause
	 *              the server to wait for larger amounts of data to accumulate
	 *              which can improve server throughput a bit at the cost of
	 *              some additional latency.
	 * @default 1
	 * @importance high
	 */
	public static final String FETCH_MIN_BYTES = "fetch.min.bytes";
	/**
	 * @type string
	 * @description A unique string that identifies the consumer group this
	 *              consumer belongs to. This property is required if the
	 *              consumer uses either the group management functionality by
	 *              using subscribe(topic) or the Kafka-based offset management
	 *              strategy.
	 * @default ""
	 * @importance high
	 */
	public static final String GROUP_ID = "group.id";
	/**
	 * @type int
	 * @description The expected time between heartbeats to the consumer
	 *              coordinator when using Kafka's group management facilities.
	 *              Heartbeats are used to ensure that the consumer's session
	 *              stays active and to facilitate rebalancing when new
	 *              consumers join or leave the group. The value must be set
	 *              lower than session.timeout.ms, but typically should be set
	 *              no higher than 1/3 of that value. It can be adjusted even
	 *              lower to control the expected time for normal rebalances.
	 * @default 3000
	 * @importance high
	 */
	public static final String HEARTBEAT_INTERVAL_MS = "heartbeat.interval.ms";
	/**
	 * @type int
	 * @description The maximum amount of data per-partition the server will
	 *              return. Records are fetched in batches by the consumer. If
	 *              the first record batch in the first non-empty partition of
	 *              the fetch is larger than this limit, the batch will still be
	 *              returned to ensure that the consumer can make progress. The
	 *              maximum record batch size accepted by the broker is defined
	 *              via message.max.bytes (broker config) or max.message.bytes
	 *              (topic config). See fetch.max.bytes for limiting the
	 *              consumer request size.
	 * @default 1048576
	 * @importance high
	 */
	public static final String MAX_PARTITION_FETCH_BYTES = "max.partition.fetch.bytes";
	/**
	 * @type int
	 * @description The timeout used to detect consumer failures when using
	 *              Kafka's group management facility. The consumer sends
	 *              periodic heartbeats to indicate its liveness to the broker.
	 *              If no heartbeats are received by the broker before the
	 *              expiration of this session timeout, then the broker will
	 *              remove this consumer from the group and initiate a
	 *              rebalance. Note that the value must be in the allowable
	 *              range as configured in the broker configuration by
	 *              group.min.session.timeout.ms and
	 *              group.max.session.timeout.ms
	 * @default 10000
	 * @importance high
	 */
	public static final String SESSION_TIMEOUT_MS = "session.timeout.ms";
	/**
	 * @type password
	 * @description The password of the private key in the key store file. This
	 *              is optional for client.
	 * @default null
	 * @importance high
	 */
	public static final String SSL_KEY_PASSWORD = "ssl.key.password";
	/**
	 * @type string
	 * @description The location of the key store file. This is optional for
	 *              client and can be used for two-way authentication for
	 *              client.
	 * @default null
	 * @importance high
	 */
	public static final String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
	/**
	 * @type password
	 * @description The store password for the key store file. This is optional
	 *              for client and only needed if ssl.keystore.location is
	 *              configured.
	 * @default null
	 * @importance high
	 */
	public static final String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
	/**
	 * @type string
	 * @description The location of the trust store file.
	 * @default null
	 * @importance high
	 */
	public static final String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
	/**
	 * @type password
	 * @description The password for the trust store file. If a password is not
	 *              set access to the truststore is still available, but
	 *              integrity checking is disabled.
	 * @default null
	 * @importance high
	 */
	public static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
	/**
	 * @type
	 * @description
	 * @default
	 * @importance
	 */
	public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
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
	public static final String ENABLE_AUTO_COMMIT = "enable.auto.commit";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String FETCH_MAX_BYTES = "fetch.max.bytes";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String ISOLATION_LEVEL = "isolation.level";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String MAX_POLL_INTERVAL_MS = "max.poll.interval.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String MAX_POLL_RECORDS = "max.poll.records";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String PARTITION_ASSIGNMENT_STRATEGY = "partition.assignment.strategy";
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
	public static final String END_BUFFER_BYTES = "send.buffer.bytes";
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
	public static final String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
	/**
	 * @type
	 * @description
	 * @default
	 * @example
	 * @importance
	 */
	public static final String CHECK_CRCS = "check.crcs";
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
	public static final String FETCH_MAX_WAIT_MS = "fetch.max.wait.ms";
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
	public static final String METRICS_SAMPLE_WINDOW_MS = "etrics.sample.window.ms";
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

}
