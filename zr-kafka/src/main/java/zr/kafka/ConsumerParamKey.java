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
	 * @example
	 * @importance high
	 */
	public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
	/**
	 * @type class
	 * @description Deserializer class for key that implements the Deserializer
	 *              interface.
	 * @default
	 * @example org.apache.kafka.common.serialization.StringDeserializer
	 * @importance high
	 */
	public static final String KEY_DESERIALIZER = "key.deserializer";
	/**
	 * @type class
	 * @description Deserializer class for value that implements the
	 *              Deserializer interface.
	 * @default
	 * @example org.apache.kafka.common.serialization.StringDeserializer
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
	 * @example
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
	 * @example
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
	 * @example
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
	 * @example
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
	 * @example
	 * @importance high
	 */
	public static final String SESSION_TIMEOUT_MS = "session.timeout.ms";
	/**
	 * @type password
	 * @description The password of the private key in the key store file. This
	 *              is optional for client.
	 * @default null
	 * @example
	 * @importance high
	 */
	public static final String SSL_KEY_PASSWORD = "ssl.key.password";
	/**
	 * @type string
	 * @description The location of the key store file. This is optional for
	 *              client and can be used for two-way authentication for
	 *              client.
	 * @default null
	 * @example
	 * @importance high
	 */
	public static final String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
	/**
	 * @type password
	 * @description The store password for the key store file. This is optional
	 *              for client and only needed if ssl.keystore.location is
	 *              configured.
	 * @default null
	 * @example
	 * @importance high
	 */
	public static final String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
	/**
	 * @type string
	 * @description The location of the trust store file.
	 * @default null
	 * @example
	 * @importance high
	 */
	public static final String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
	/**
	 * @type password
	 * @description The password for the trust store file. If a password is not
	 *              set access to the truststore is still available, but
	 *              integrity checking is disabled.
	 * @default null
	 * @example
	 * @importance high
	 */
	public static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
	/**
	 * @type string
	 * @description What to do when there is no initial offset in Kafka or if
	 *              the current offset does not exist any more on the server
	 *              (e.g. because that data has been deleted):
	 * 
	 *              earliest: automatically reset the offset to the earliest
	 *              offset
	 * 
	 *              latest: automatically reset the offset to the latest offset
	 * 
	 *              none: throw exception to the consumer if no previous offset
	 *              is found for the consumer's group anything else: throw
	 *              exception to the consumer.
	 * @default latest
	 * @example
	 * @importance medium
	 */
	public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
	/**
	 * @type long
	 * @description Close idle connections after the number of milliseconds
	 *              specified by this config.
	 * @default 540000
	 * @example
	 * @importance medium
	 */
	public static final String CONNECTIONS_MAX_IDLE_MS = "connections.max.idle.ms";
	/**
	 * @type boolean
	 * @description If true the consumer's offset will be periodically committed
	 *              in the background.
	 * @default true
	 * @example
	 * @importance medium
	 */
	public static final String ENABLE_AUTO_COMMIT = "enable.auto.commit";
	/**
	 * @type boolean
	 * @description Whether records from internal topics (such as offsets)
	 *              should be exposed to the consumer. If set to true the only
	 *              way to receive records from an internal topic is subscribing
	 *              to it.
	 * @default true
	 * @example
	 * @importance medium
	 */
	public static final String EXCLUDE_INTERNAL_TOPICS = "exclude.internal.topics";
	/**
	 * @type int
	 * @description The maximum amount of data the server should return for a
	 *              fetch request. Records are fetched in batches by the
	 *              consumer, and if the first record batch in the first
	 *              non-empty partition of the fetch is larger than this value,
	 *              the record batch will still be returned to ensure that the
	 *              consumer can make progress. As such, this is not a absolute
	 *              maximum. The maximum record batch size accepted by the
	 *              broker is defined via message.max.bytes (broker config) or
	 *              max.message.bytes (topic config). Note that the consumer
	 *              performs multiple fetches in parallel.
	 * @default 52428800
	 * @example
	 * @importance medium
	 */
	public static final String FETCH_MAX_BYTES = "fetch.max.bytes";
	/**
	 * @type string
	 * @description Controls how to read messages written transactionally. If
	 *              set to read_committed, consumer.poll() will only return
	 *              transactional messages which have been committed. If set to
	 *              read_uncommitted' (the default), consumer.poll() will return
	 *              all messages, even transactional messages which have been
	 *              aborted. Non-transactional messages will be returned
	 *              unconditionally in either mode.
	 * 
	 *              Messages will always be returned in offset order. Hence, in
	 *              read_committed mode, consumer.poll() will only return
	 *              messages up to the last stable offset (LSO), which is the
	 *              one less than the offset of the first open transaction. In
	 *              particular any messages appearing after messages belonging
	 *              to ongoing transactions will be withheld until the relevant
	 *              transaction has been completed. As a result, read_committed
	 *              consumers will not be able to read up to the high watermark
	 *              when there are in flight transactions.
	 * 
	 *              Further, when in read_committed the seekToEnd method will
	 *              return the LSO
	 * @default read_uncommitted
	 * @example
	 * @importance medium
	 */
	public static final String ISOLATION_LEVEL = "isolation.level";
	/**
	 * @type int
	 * @description The maximum delay between invocations of poll() when using
	 *              consumer group management. This places an upper bound on the
	 *              amount of time that the consumer can be idle before fetching
	 *              more records. If poll() is not called before expiration of
	 *              this timeout, then the consumer is considered failed and the
	 *              group will rebalance in order to reassign the partitions to
	 *              another member.
	 * @default 300000
	 * @example
	 * @importance medium
	 */
	public static final String MAX_POLL_INTERVAL_MS = "max.poll.interval.ms";
	/**
	 * @type int
	 * @description The maximum number of records returned in a single call to
	 *              poll().
	 * @default 500
	 * @example
	 * @importance medium
	 */
	public static final String MAX_POLL_RECORDS = "max.poll.records";
	/**
	 * @type class
	 * @description The class name of the partition assignment strategy that the
	 *              client will use to distribute partition ownership amongst
	 *              consumer instances when group management is used
	 * @default class org.apache.kafka.clients.consumer.RangeAssignor
	 * @example
	 * @importance medium
	 */
	public static final String PARTITION_ASSIGNMENT_STRATEGY = "partition.assignment.strategy";
	/**
	 * @type int
	 * @description The size of the TCP receive buffer (SO_RCVBUF) to use when
	 *              reading data. If the value is -1, the OS default will be
	 *              used.
	 * @default 65536
	 * @example
	 * @importance medium
	 */
	public static final String RECEIVE_BUFFER_BYTES = "receive.buffer.bytes";
	/**
	 * @type int
	 * @description The configuration controls the maximum amount of time the
	 *              client will wait for the response of a request. If the
	 *              response is not received before the timeout elapses the
	 *              client will resend the request if necessary or fail the
	 *              request if retries are exhausted.
	 * @default 305000
	 * @example
	 * @importance medium
	 */
	public static final String REQUEST_TIMEOUT_MS = "request.timeout.ms";
	/**
	 * @type password
	 * @description JAAS login context parameters for SASL connections in the
	 *              format used by JAAS configuration files. JAAS configuration
	 *              file format is described here. The format for the value is:
	 *              ' (=)*;'
	 * @default null
	 * @example
	 * @importance medium
	 */
	public static final String SASL_JAAS_CONFIG = "sasl.jaas.config";
	/**
	 * @type string
	 * @description The Kerberos principal name that Kafka runs as. This can be
	 *              defined either in Kafka's JAAS config or in Kafka's config.
	 * @default null
	 * @example
	 * @importance medium
	 */
	public static final String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
	/**
	 * @type string
	 * @description SASL mechanism used for client connections. This may be any
	 *              mechanism for which a security provider is available. GSSAPI
	 *              is the default mechanism.
	 * @default GSSAPI
	 * @example
	 * @importance medium
	 */
	public static final String SASL_MECHANISM = "sasl.mechanism";
	/**
	 * @type string
	 * @description Protocol used to communicate with brokers. Valid values are:
	 *              PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.
	 * @default PLAINTEXT
	 * @example
	 * @importance medium
	 */
	public static final String SECURITY_PROTOCOL = "security.protocol";
	/**
	 * @type int
	 * @description The size of the TCP send buffer (SO_SNDBUF) to use when
	 *              sending data. If the value is -1, the OS default will be
	 *              used.
	 * @default 131072
	 * @example
	 * @importance medium
	 */
	public static final String END_BUFFER_BYTES = "send.buffer.bytes";
	/**
	 * @type list
	 * @description The list of protocols enabled for SSL connections.
	 * @default TLSv1.2,TLSv1.1,TLSv1
	 * @example
	 * @importance medium
	 */
	public static final String SSL_ENABLED_PROTOCOLS = "ssl.enabled.protocols";
	/**
	 * @type string
	 * @description The file format of the key store file. This is optional for
	 *              client.
	 * @default JKS
	 * @example
	 * @importance medium
	 */
	public static final String SSL_KEYSTORE_TYPE = "ssl.keystore.type";
	/**
	 * @type string
	 * @description The SSL protocol used to generate the SSLContext. Default
	 *              setting is TLS, which is fine for most cases. Allowed values
	 *              in recent JVMs are TLS, TLSv1.1 and TLSv1.2. SSL, SSLv2 and
	 *              SSLv3 may be supported in older JVMs, but their usage is
	 *              discouraged due to known security vulnerabilities.
	 * @default TLS
	 * @example
	 * @importance medium
	 */
	public static final String SSL_PROTOCOL = "ssl.protocol";
	/**
	 * @type string
	 * @description The name of the security provider used for SSL connections.
	 *              Default value is the default security provider of the JVM.
	 * @default null
	 * @example
	 * @importance medium
	 */
	public static final String SSL_PROVIDER = "ssl.provider";
	/**
	 * @type string
	 * @description The file format of the trust store file.
	 * @default JKS
	 * @example
	 * @importance medium
	 */
	public static final String SSL_TRUSTSTORE_TYPE = "ssl.truststore.type";
	/**
	 * @type int
	 * @description The frequency in milliseconds that the consumer offsets are
	 *              auto-committed to Kafka if enable.auto.commit is set to
	 *              true.
	 * @default 5000
	 * @example
	 * @importance low
	 */
	public static final String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
	/**
	 * @type boolean
	 * @description Automatically check the CRC32 of the records consumed. This
	 *              ensures no on-the-wire or on-disk corruption to the messages
	 *              occurred. This check adds some overhead, so it may be
	 *              disabled in cases seeking extreme performance.
	 * @default true
	 * @example
	 * @importance low
	 */
	public static final String CHECK_CRCS = "check.crcs";
	/**
	 * @type string
	 * @description An id string to pass to the server when making requests. The
	 *              purpose of this is to be able to track the source of
	 *              requests beyond just ip/port by allowing a logical
	 *              application name to be included in server-side request
	 *              logging.
	 * @default ""
	 * @example
	 * @importance low
	 */
	public static final String CLIENT_ID = "client.id";
	/**
	 * @type int
	 * @description The maximum amount of time the server will block before
	 *              answering the fetch request if there isn't sufficient data
	 *              to immediately satisfy the requirement given by
	 *              fetch.min.bytes.
	 * @default 500
	 * @example
	 * @importance low
	 */
	public static final String FETCH_MAX_WAIT_MS = "fetch.max.wait.ms";
	/**
	 * @type list
	 * @description A list of classes to use as interceptors. Implementing the
	 *              ConsumerInterceptor interface allows you to intercept (and
	 *              possibly mutate) records received by the consumer. By
	 *              default, there are no interceptors.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String INTERCEPTOR_CLASSES = "interceptor.classes";
	/**
	 * @type long
	 * @description The period of time in milliseconds after which we force a
	 *              refresh of metadata even if we haven't seen any partition
	 *              leadership changes to proactively discover any new brokers
	 *              or partitions.
	 * @default 300000
	 * @example
	 * @importance low
	 */
	public static final String METADATA_MAX_AGE_MS = "metadata.max.age.ms";
	/**
	 * @type list
	 * @description A list of classes to use as metrics reporters. Implementing
	 *              the MetricReporter interface allows plugging in classes that
	 *              will be notified of new metric creation. The JmxReporter is
	 *              always included to register JMX statistics.
	 * @default ""
	 * @example
	 * @importance low
	 */
	public static final String METRIC_REPORTERS = "metric.reporters";
	/**
	 * @type int
	 * @description The number of samples maintained to compute metrics.
	 * @default 2
	 * @example
	 * @importance low
	 */
	public static final String METRICS_NUM_SAMPLES = "metrics.num.samples";
	/**
	 * @type string
	 * @description The highest recording level for metrics.
	 * @default INFO
	 * @example
	 * @importance low
	 */
	public static final String METRICS_RECORDING_LEVEL = "metrics.recording.level";
	/**
	 * @type long
	 * @description The window of time a metrics sample is computed over.
	 * @default 30000
	 * @example
	 * @importance low
	 */
	public static final String METRICS_SAMPLE_WINDOW_MS = "etrics.sample.window.ms";
	/**
	 * @type long
	 * @description The maximum amount of time in milliseconds to wait when
	 *              reconnectng to a broker that has repeatedly failed to
	 *              connect. If provided, the backoff per host will increase
	 *              exponentially for each consecutive connection failure, up to
	 *              this maximum. After calculating the backoff increase, 20%
	 *              random jitter is added to avoid connection storms.
	 * @default 1000
	 * @example
	 * @importance low
	 */
	public static final String RECONNECT_BACKOFF_MAX_MS = "reconnect.backoff.max.ms";
	/**
	 * @type long
	 * @description The base amount of time to wait before attempting to
	 *              reconnect to a given host. This avoids repeatedly connecting
	 *              to a host in a tight loop. This backoff applies to all
	 *              connection attempts by the client to a broker.
	 * @default 50
	 * @example
	 * @importance low
	 */
	public static final String RECONNECT_BACKOFF_MS = "reconnect.backoff.ms";
	/**
	 * @type long
	 * @description The amount of time to wait before attempting to retry a
	 *              failed request to a given topic partition. This avoids
	 *              repeatedly sending requests in a tight loop under some
	 *              failure scenarios.
	 * @default 100
	 * @example
	 * @importance low
	 */
	public static final String RETRY_BACKOFF_MS = "retry.backoff.ms";
	/**
	 * @type string
	 * @description Kerberos kinit command path.
	 * @default /usr/bin/kinit
	 * @example
	 * @importance low
	 */
	public static final String SASL_KERBEROS_KINIT_CMD = "sasl.kerberos.kinit.cmd";
	/**
	 * @type long
	 * @description Login thread sleep time between refresh attempts.
	 * @default 60000
	 * @example
	 * @importance low
	 */
	public static final String SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN = "sasl.kerberos.min.time.before.relogin";
	/**
	 * @type double
	 * @description Percentage of random jitter added to the renewal time.
	 * @default 0.05
	 * @example
	 * @importance low
	 */
	public static final String SASL_KERBEROS_TICKET_RENEW_JITTER = "sasl.kerberos.ticket.renew.jitter";
	/**
	 * @type double
	 * @description Login thread will sleep until the specified window factor of
	 *              time from last refresh to ticket's expiry has been reached,
	 *              at which time it will try to renew the ticket.
	 * @default 0.8
	 * @example
	 * @importance low
	 */
	public static final String SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR = "sasl.kerberos.ticket.renew.window.factor";
	/**
	 * @type list
	 * @description A list of cipher suites. This is a named combination of
	 *              authentication, encryption, MAC and key exchange algorithm
	 *              used to negotiate the security settings for a network
	 *              connection using TLS or SSL network protocol. By default all
	 *              the available cipher suites are supported.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String SSL_CIPHER_SUITES = "ssl.cipher.suites";
	/**
	 * @type string
	 * @description The endpoint identification algorithm to validate server
	 *              hostname using server certificate.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String SSL_ENDPOINT_IDENTIFICATION_ALGORITHM = "ssl.endpoint.identification.algorithm";
	/**
	 * @type string
	 * @description The algorithm used by key manager factory for SSL
	 *              connections. Default value is the key manager factory
	 *              algorithm configured for the Java Virtual Machine.
	 * @default SunX509
	 * @example
	 * @importance low
	 */
	public static final String SSL_KEYMANAGER_ALGORITHM = "ssl.keymanager.algorithm";
	/**
	 * @type string
	 * @description The SecureRandom PRNG implementation to use for SSL
	 *              cryptography operations.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String SSL_SECURE_RANDOM_IMPLEMENTATION = "ssl.secure.random.implementation";
	/**
	 * @type string
	 * @description The algorithm used by trust manager factory for SSL
	 *              connections. Default value is the trust manager factory
	 *              algorithm configured for the Java Virtual Machine.
	 * @default PKIX
	 * @example
	 * @importance low
	 */
	public static final String SSL_TRUSTMANAGER_ALGORITHM = "ssl.trustmanager.algorithm";

}
