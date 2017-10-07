package zr.kafka.util;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectSerializer implements Serializer<Object>, Deserializer<Object> {
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, Object data) {
		return null;
	}

	@Override
	public Object deserialize(String topic, byte[] data) {
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
