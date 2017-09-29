package zr.kafka.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.TopicPartition;

import v.common.util.AutoArray;

public class TopicPartitionUtil {
	private static final Map<String, AutoArray<TopicPartition>> topicMap = new HashMap<>();

	public static final TopicPartition get(String topic, int partition) {
		AutoArray<TopicPartition> parts = topicMap.get(topic);
		if (parts == null)
			parts = createParts(topic);
		TopicPartition part = parts.opt(partition);
		if (part == null)
			synchronized (parts) {
				if ((part = parts.opt(partition)) == null)
					parts.set(partition, part = new TopicPartition(topic, partition));
			}
		return part;
	}

	private static final synchronized AutoArray<TopicPartition> createParts(String topic) {
		AutoArray<TopicPartition> parts = topicMap.get(topic);
		if (parts == null) {
			parts = new AutoArray<>();
			topicMap.put(topic, parts);
		}
		return parts;
	}

}
