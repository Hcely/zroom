package zr.kafka;

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
