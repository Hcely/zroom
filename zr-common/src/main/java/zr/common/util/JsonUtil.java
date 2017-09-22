package zr.common.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

public class JsonUtil {
	public static final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

	public static final String objToJson(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			return null;
		}
	}

	public static final <T> T jsonToObj(final String json, final Class<? extends T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			return null;
		}
	}

	public static final <T> List<T> jsonToList(final String json, final Class<? extends T> clazz) {
		CollectionType type = mapper.getTypeFactory().constructCollectionType(LinkedList.class, clazz);
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			return null;
		}
	}

	public static final <T> Map<String, T> jsonToMap(final String json, final Class<? extends T> clazz) {
		MapType type = mapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, clazz);
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			return null;
		}
	}

}
