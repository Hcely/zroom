package zr.monitor.statistic;

public interface ZRStatisticHandler {
	public void onHandle(ZRStatisticTask tasks);

	public void onMachineStatus();

	public void onServerStatus();
}
