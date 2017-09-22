package zr.monitor;

import java.util.Map;
import java.util.Map.Entry;

public class ZRParamUtil {
	protected static volatile Map<String, String> params;
	protected static ZRParamHandler paramsHandler;

	public static final String get(String key) {
		Map<String, String> params = ZRParamUtil.params;
		if (params == null)
			return null;
		return params.get(key);
	}

	public static final String get(String key, String nullback) {
		String hr = get(key);
		if (hr == null)
			return nullback;
		return hr;
	}

	public static final Map<String, String> getParams() {
		return params;
	}

	protected static final void setParams(Map<String, String> params) {
		Map<String, String> tmp = ZRParamUtil.params;
		ZRParamUtil.params = params;
		ZRParamHandler paramsHandler = ZRParamUtil.paramsHandler;
		if (paramsHandler == null)
			return;
		if (tmp == null)
			paramsHandler.onInit(params);
		else {
			for (Entry<String, String> e : params.entrySet()) {
				String key = e.getKey(), newValue = e.getValue();
				String oldValue = tmp.get(key);
				if (!newValue.equals(oldValue))
					paramsHandler.onParamChange(key, oldValue, newValue);
			}
			for (String e : tmp.keySet())
				if (!params.containsKey(e))
					paramsHandler.onParamRemove(e);
		}
	}

	public static void setParamsHandler(ZRParamHandler paramsHandler) {
		ZRParamUtil.paramsHandler = paramsHandler;
	}

}
