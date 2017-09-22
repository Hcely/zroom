package zr.monitor;

import java.util.Map;

public interface ZRParamHandler {

	public void onInit(Map<String, String> params);

	public void onParamChange(String key, String oldValue, String newValue);

	public void onParamRemove(String key);
}
