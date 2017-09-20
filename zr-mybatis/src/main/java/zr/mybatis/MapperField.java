package zr.mybatis;

import java.lang.reflect.Field;

import zr.mybatis.annotation.MapperConfig;

final class MapperField {
	final Field field;
	final MapperConfig config;

	MapperField(Field field, MapperConfig config) {
		this.field = field;
		this.config = config;
	}
}
