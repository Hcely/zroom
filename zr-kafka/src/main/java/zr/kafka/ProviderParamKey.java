package zr.kafka;

public class ProviderParamKey {
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
	 * @description Serializer class for key that implements the Serializer
	 *              interface.
	 * @default
	 * @example org.apache.kafka.common.serialization.StringSerializer
	 * @importance high
	 */
	public static final String KEY_SERIALIZER = "key.serializer";
	/**
	 * @type class
	 * @description Serializer class for value that implements the Serializer
	 *              interface.
	 * @default
	 * @example org.apache.kafka.common.serialization.StringSerializer
	 * @importance high
	 */
	public static final String VALUE_SERIALIZER = "value.serializer";
	/**
	 * @type string
	 * @description The number of acknowledgments the producer requires the
	 *              leader to have received before considering a request
	 *              complete. This controls the durability of records that are
	 *              sent. The following settings are allowed: acks=0 If set to
	 *              zero then the producer will not wait for any acknowledgment
	 *              from the server at all. The record will be immediately added
	 *              to the socket buffer and considered sent. No guarantee can
	 *              be made that the server has received the record in this
	 *              case, and the retries configuration will not take effect (as
	 *              the client won't generally know of any failures). The offset
	 *              given back for each record will always be set to -1. acks=1
	 *              This will mean the leader will write the record to its local
	 *              log but will respond without awaiting full acknowledgement
	 *              from all followers. In this case should the leader fail
	 *              immediately after acknowledging the record but before the
	 *              followers have replicated it then the record will be lost.
	 *              acks=all This means the leader will wait for the full set of
	 *              in-sync replicas to acknowledge the record. This guarantees
	 *              that the record will not be lost as long as at least one
	 *              in-sync replica remains alive. This is the strongest
	 *              available guarantee. This is equivalent to the acks=-1
	 *              setting.
	 * @default 1
	 * @example
	 * @importance high
	 */
	public static final String ACKS = "acks";
	/**
	 * @type long
	 * @description The total bytes of memory the producer can use to buffer
	 *              records waiting to be sent to the server. If records are
	 *              sent faster than they can be delivered to the server the
	 *              producer will block for max.block.ms after which it will
	 *              throw an exception. This setting should correspond roughly
	 *              to the total memory the producer will use, but is not a hard
	 *              bound since not all memory the producer uses is used for
	 *              buffering. Some additional memory will be used for
	 *              compression (if compression is enabled) as well as for
	 *              maintaining in-flight requests.
	 * @default 33554432
	 * @example
	 * @importance high
	 */
	public static final String BUFFER_MEMORY = "buffer.memory";
	/**
	 * @type string
	 * @description The compression type for all data generated by the producer.
	 *              The default is none (i.e. no compression). Valid values are
	 *              none, gzip, snappy, or lz4. Compression is of full batches
	 *              of data, so the efficacy of batching will also impact the
	 *              compression ratio (more batching means better compression).
	 * @default none
	 * @example
	 * @importance high
	 */
	public static final String COMPRESSION_TYPE = "compression.type";
	/**
	 * @type int
	 * @description Setting a value greater than zero will cause the client to
	 *              resend any record whose send fails with a potentially
	 *              transient error. Note that this retry is no different than
	 *              if the client resent the record upon receiving the error.
	 *              Allowing retries without setting
	 *              max.in.flight.requests.per.connection to 1 will potentially
	 *              change the ordering of records because if two batches are
	 *              sent to a single partition, and the first fails and is
	 *              retried but the second succeeds, then the records in the
	 *              second batch may appear first.
	 * @default 0
	 * @example
	 * @importance high
	 */
	public static final String RETRIES = "retries";
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
	 * @type int
	 * @description The producer will attempt to batch records together into
	 *              fewer requests whenever multiple records are being sent to
	 *              the same partition. This helps performance on both the
	 *              client and the server. This configuration controls the
	 *              default batch size in bytes. No attempt will be made to
	 *              batch records larger than this size.
	 * 
	 *              Requests sent to brokers will contain multiple batches, one
	 *              for each partition with data available to be sent.
	 * 
	 *              A small batch size will make batching less common and may
	 *              reduce throughput (a batch size of zero will disable
	 *              batching entirely). A very large batch size may use memory a
	 *              bit more wastefully as we will always allocate a buffer of
	 *              the specified batch size in anticipation of additional
	 *              records.
	 * @default 16384
	 * @example
	 * @importance medium
	 */
	public static final String BATCH_SIZE = "batch.size";
	/**
	 * @type string
	 * @description An id string to pass to the server when making requests. The
	 *              purpose of this is to be able to track the source of
	 *              requests beyond just ip/port by allowing a logical
	 *              application name to be included in server-side request
	 *              logging.
	 * @default ""
	 * @example
	 * @importance medium
	 */
	public static final String CLIENT_ID = "client.id";
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
	 * @type long
	 * @description The producer groups together any records that arrive in
	 *              between request transmissions into a single batched request.
	 *              Normally this occurs only under load when records arrive
	 *              faster than they can be sent out. However in some
	 *              circumstances the client may want to reduce the number of
	 *              requests even under moderate load. This setting accomplishes
	 *              this by adding a small amount of artificial delay—that is,
	 *              rather than immediately sending out a record the producer
	 *              will wait for up to the given delay to allow other records
	 *              to be sent so that the sends can be batched together. This
	 *              can be thought of as analogous to Nagle's algorithm in TCP.
	 *              This setting gives the upper bound on the delay for
	 *              batching: once we get batch.size worth of records for a
	 *              partition it will be sent immediately regardless of this
	 *              setting, however if we have fewer than this many bytes
	 *              accumulated for this partition we will 'linger' for the
	 *              specified time waiting for more records to show up. This
	 *              setting defaults to 0 (i.e. no delay). Setting linger.ms=5,
	 *              for example, would have the effect of reducing the number of
	 *              requests sent but would add up to 5ms of latency to records
	 *              sent in the absense of load.
	 * @default 0
	 * @example
	 * @importance medium
	 */
	public static final String LINGER_MS = "linger.ms";
	/**
	 * @type long
	 * @description The configuration controls how long KafkaProducer.send() and
	 *              KafkaProducer.partitionsFor() will block.These methods can
	 *              be blocked either because the buffer is full or metadata
	 *              unavailable.Blocking in the user-supplied serializers or
	 *              partitioner will not be counted against this timeout.
	 * @default 60000
	 * @example
	 * @importance medium
	 */
	public static final String MAX_BLOCK_MS = "max.block.ms";
	/**
	 * @type int
	 * @description The maximum size of a request in bytes. This setting will
	 *              limit the number of record batches the producer will send in
	 *              a single request to avoid sending huge requests. This is
	 *              also effectively a cap on the maximum record batch size.
	 *              Note that the server has its own cap on record batch size
	 *              which may be different from this.
	 * @default 1048576
	 * @example
	 * @importance medium
	 */
	public static final String MAX_REQUEST_SIZE = "max.request.size";

	/**
	 * @type class
	 * @description Partitioner class that implements the Partitioner interface.
	 * @default org.apache.kafka.clients.producer.internals.DefaultPartitioner
	 * @example
	 * @importance medium
	 */
	public static final String PARTITIONER_CLASS = "partitioner.class";
	/**
	 * @type int
	 * @description The size of the TCP receive buffer (SO_RCVBUF) to use when
	 *              reading data. If the value is -1, the OS default will be
	 *              used.
	 * @default 32768
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
	 *              request if retries are exhausted. This should be larger than
	 *              replica.lag.time.max.ms (a broker configuration) to reduce
	 *              the possibility of message duplication due to unnecessary
	 *              producer retries.
	 * @default 30000
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
	public static final String SEND_BUFFER_BYTES = "send.buffer.bytes";
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
	 * @type boolean
	 * @description When set to 'true', the producer will ensure that exactly
	 *              one copy of each message is written in the stream. If
	 *              'false', producer retries due to broker failures, etc., may
	 *              write duplicates of the retried message in the stream. This
	 *              is set to 'false' by default. Note that enabling idempotence
	 *              requires max.in.flight.requests.per.connection to be set to
	 *              1 and retries cannot be zero. Additionally acks must be set
	 *              to 'all'. If these values are left at their defaults, we
	 *              will override the default to be suitable. If the values are
	 *              set to something incompatible with the idempotent producer,
	 *              a ConfigException will be thrown.
	 * @default false
	 * @example
	 * @importance low
	 */
	public static final String ENABLE_IDEMPOTENCE = "enable.idempotence";
	/**
	 * @type list
	 * @description A list of classes to use as interceptors. Implementing the
	 *              ProducerInterceptor interface allows you to intercept (and
	 *              possibly mutate) the records received by the producer before
	 *              they are published to the Kafka cluster. By default, there
	 *              are no interceptors.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String INTERCEPTOR_CLASSES = "interceptor.classes";
	/**
	 * @type int
	 * @description The maximum number of unacknowledged requests the client
	 *              will send on a single connection before blocking. Note that
	 *              if this setting is set to be greater than 1 and there are
	 *              failed sends, there is a risk of message re-ordering due to
	 *              retries (i.e., if retries are enabled).
	 * @default 5
	 * @example
	 * @importance low
	 */
	public static final String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max.in.flight.requests.per.connection";
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
	public static final String METRICS_SAMPLE_WINDOW_MS = "metrics.sample.window.ms";
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
	/**
	 * @type int
	 * @description The maximum amount of time in ms that the transaction
	 *              coordinator will wait for a transaction status update from
	 *              the producer before proactively aborting the ongoing
	 *              transaction.If this value is larger than the
	 *              max.transaction.timeout.ms setting in the broker, the
	 *              request will fail with a `InvalidTransactionTimeout` error.
	 * @default 60000
	 * @example
	 * @importance low
	 */
	public static final String TRANSACTION_TIMEOUT_MS = "transaction.timeout.ms";
	/**
	 * @type string
	 * @description The TransactionalId to use for transactional delivery. This
	 *              enables reliability semantics which span multiple producer
	 *              sessions since it allows the client to guarantee that
	 *              transactions using the same TransactionalId have been
	 *              completed prior to starting any new transactions. If no
	 *              TransactionalId is provided, then the producer is limited to
	 *              idempotent delivery. Note that enable.idempotence must be
	 *              enabled if a TransactionalId is configured. The default is
	 *              empty, which means transactions cannot be used.
	 * @default null
	 * @example
	 * @importance low
	 */
	public static final String TRANSACTIONAL_ID = "transactional.id";
}
