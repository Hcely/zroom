package zr.kafka;

import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;

import v.ObjBuilder;
import v.VObject;

public class ZRKafkaProvider implements VObject {
	public static final class Builder implements ObjBuilder<ZRKafkaProvider> {
		protected final Properties properties = new Properties();

		public void setServers(Collection<String> servers) {
			setParam(ProviderParamKey.BOOTSTRAP_SERVERS, servers);
		}

		public void setAcks(int acks) {
			setParam(ProviderParamKey.ACKS, acks);
		}

		public void setRetries(int retries) {
			setParam(ProviderParamKey.RETRIES, retries);
		}

		public void setBatchSize(int size) {
			setParam(ProviderParamKey.BATCH_SIZE, size);
		}

		public void setBuffer(long bufferSize) {
			setParam(ProviderParamKey.BUFFER_MEMORY, bufferSize);
		}

		public void setMaxBlockMs(long ms) {
			setParam(ProviderParamKey.MAX_BLOCK_MS, ms);
		}

		public void setMaxRequestSize(long size) {
			setParam(ProviderParamKey.MAX_REQUEST_SIZE, size);
		}

		public void setRequestTimeout(long time) {
			setParam(ProviderParamKey.REQUEST_TIMEOUT_MS, time);
		}

		public void setPartitioner(Class<? extends Partitioner> clazz) {
			setParam(ProviderParamKey.PARTITIONER_CLASS, clazz);
		}

		public void setKeySerializer(Class<? extends Serializer<?>> clazz) {
			setParam(ProviderParamKey.KEY_SERIALIZER, clazz);
		}

		public void setValueSerializer(Class<? extends Serializer<?>> clazz) {
			setParam(ProviderParamKey.VALUE_SERIALIZER, clazz);
		}

		public void setConnectionIdleTime(long time) {
			setParam(ProviderParamKey.CONNECTIONS_MAX_IDLE_MS, time);
		}

		public void setInterceptor(Collection<Class<?>> clazzes) {
			setParam(ProviderParamKey.INTERCEPTOR_CLASSES, clazzes);
		}

		public void setTraId(String transId) {
			setParam(ProviderParamKey.TRANSACTIONAL_ID, transId);
		}

		public void setParam(String key, Object value) {
			if (value instanceof Collection) {
				Collection<?> list = (Collection<?>) value;
				StringBuilder sb = new StringBuilder(list.size() * 20 + 8);
				for (Object e : list) {
					if (sb.length() > 0)
						sb.append(',');
					if (e instanceof Class)
						sb.append(((Class<?>) e).getName());
					else
						sb.append(e);
				}
				if (sb.length() > 0)
					properties.put(key, sb.toString());
			} else {
				if (value instanceof Class)
					properties.put(key, ((Class<?>) value).getName());
				else
					properties.put(key, value.toString());
			}
		}

		@Override
		public ZRKafkaProvider build() {
			KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);
			if (properties.containsKey(ProviderParamKey.TRANSACTIONAL_ID))
				producer.initTransactions();
			return new ZRKafkaProvider(producer);
		}

		@Override
		public Class<ZRKafkaProvider> getType() {
			return ZRKafkaProvider.class;
		}
	}

	protected boolean destory;
	protected final KafkaProducer<Object, Object> producer;

	private ZRKafkaProvider(KafkaProducer<Object, Object> producer) {
		this.producer = producer;
		this.destory = false;
	}

	@Override
	public void destory() {
		if (destory)
			return;
		synchronized (this) {
			if (destory)
				return;
			destory = true;
		}
		producer.close();
	}

	@Override
	public boolean isInit() {
		return true;
	}

	@Override
	public boolean isDestory() {
		return destory;
	}

	public void send(String topic, Object key, Object value) {
		producer.send(new ProducerRecord<>(topic, key, value));
	}

	public void send(String topic, int partition, Object key, Object value) {
		producer.send(new ProducerRecord<>(topic, partition, key, value));
	}

	public void begin() {
		producer.beginTransaction();
	}

	public void rollback() {
		producer.abortTransaction();
	}

	public void commit() {
		producer.commitTransaction();
	}
}
