package zr.kafka;

import java.util.Properties;

import v.ObjBuilder;
import v.VObject;

public class ZRKafkaProvider implements VObject {
	public static final class Builder implements ObjBuilder<ZRKafkaProvider> {
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
