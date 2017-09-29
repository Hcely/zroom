package zr.kafka;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import v.ObjBuilder;
import v.VObject;

public class ZRKafkaConsumer implements VObject {

	public static final class Builder implements ObjBuilder<ZRKafkaConsumer> {

		@Override
		public ZRKafkaConsumer build() {
			return null;
		}

		@Override
		public Class<ZRKafkaConsumer> getType() {
			return ZRKafkaConsumer.class;
		}

	}

	protected final KafkaConsumer<Object, Object> consumer;
	protected boolean destory;

	public ZRKafkaConsumer(KafkaConsumer<Object, Object> consumer) {
		this.consumer = consumer;
		this.destory = false;
	}

	public void subscribe(String... topics) {
		consumer.subscribe(Arrays.asList(topics));
	}

	public void unsubcribe() {
		consumer.unsubscribe();
	}

	public ConsumerRecords<Object, Object> poll() {
		return consumer.poll(100);
	}

	public void commitOffset(TopicPartition partition, long offset) {
		consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(offset)));
	}

	public void commitOffset(Map<TopicPartition, OffsetAndMetadata> offsets) {
		consumer.commitSync(offsets);
	}

	public void seek(TopicPartition partition, long offset) {
		consumer.seek(partition, offset);
	}

	public void seekToBeginning(Collection<TopicPartition> partitions) {
		consumer.seekToBeginning(partitions);
	}

	public void seekToEnd(Collection<TopicPartition> partitions) {
		consumer.seekToEnd(partitions);
	}

	public long position(TopicPartition partition) {
		return consumer.position(partition);
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
		consumer.close();
	}

	@Override
	public boolean isInit() {
		return true;
	}

	@Override
	public boolean isDestory() {
		return destory;
	}

}
