package zr.monitor;

public interface ZRLogContent {
	public void setLogContent(String log);

	public void addFlag(String key, String value);

	public void clearFlags();
}
