package zr.monitor.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import v.common.helper.NumberHelper;
import v.common.helper.StrUtil;
import v.server.helper.NetUtil;

public final class ZRMonitorUtil {
	private static final char[] STR_FORMAT = { '0', '0', '0', '0', '0', '0', '0' };
	private static final char[] SILK_ID_FORMAT = { '0', '0', '0', '0', '0', '0', '0', '-', '0', '0' };
	private static final char[] ZR_REQ_ID_FORMAT = { 'z', 'r', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
			'0', '0', '0' };
	private static final int machineCode = 0xFFFF & NetUtil.getMachineCode();
	private static final AtomicInteger incNum = new AtomicInteger(0);

	public static final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

	public static final String getApiKey(String methodName, String version) {
		StringBuilder sb = new StringBuilder(methodName.length() + version.length() + 1);
		sb.append(methodName).append('-').append(version);
		return StrUtil.sbToString(sb);
	}

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

	public static final String getBinFolder() {
		try {
			return new File("").getCanonicalPath();
		} catch (IOException e) {
			return "";
		}
	}

	public static final String getStartClassPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		if (url == null)
			return null;
		return url.getPath();
	}

	public static final String getMachineIp() {
		return NetUtil.getOutputAddr4().getHostAddress();
	}

	public static final String getServerId() {
		StringBuilder sb = new StringBuilder(128);
		buildServerStr(sb);
		sb.setLength(0);
		sb.append(getMachineIp()).append('-').append(get32Hashcode(StrUtil.hashCode(sb)));
		return sb.toString();
	}

	public static final String getServiceId() {
		StringBuilder sb = new StringBuilder(256);
		buildServiceStr(sb);
		sb.setLength(0);
		sb.append(getServerId()).append('-').append(get32Hashcode(StrUtil.hashCode(sb)));
		return sb.toString();
	}

	private static final char[] get32Hashcode(int hashcode) {
		char[] c = STR_FORMAT.clone();
		NumberHelper.to32Str(c, hashcode, 0, 7);
		return c;
	}

	public static final String buildSilkId(int hashcode, int idx) {
		char[] c = SILK_ID_FORMAT.clone();
		NumberHelper.to32Str(c, hashcode, 0, 7);
		NumberHelper.to32Str(c, idx, 8, 2);
		return StrUtil.newStr(c);
	}

	public static final String getReqId() {
		long l = System.currentTimeMillis() << 16;
		l |= machineCode;
		int inc = incNum.getAndIncrement();
		char[] c = ZR_REQ_ID_FORMAT.clone();
		NumberHelper.to32Str(c, l, 2, 12);
		c[14] = NumberHelper.CHAR_64[(inc >>> 5) & 31];
		c[15] = NumberHelper.CHAR_64[inc & 31];
		return StrUtil.newStr(c);
	}

	private static final void buildServerStr(StringBuilder sb) {
		sb.append("ip:").append(getMachineIp()).append(';');
		sb.append("path:").append(getBinFolder()).append(';');
	}

	private static final void buildServiceStr(StringBuilder sb) {
		buildServerStr(sb);
		sb.append("startClass:").append(getStartClassPath()).append(';');
		sb.append("classpath:").append(System.getProperty("java.class.path")).append(';');
	}

}
