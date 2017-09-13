package zr.monitor.cluster.client;

import java.util.List;

import zr.monitor.bean.info.ZRApiInfo;
import zr.monitor.bean.info.ZRAuthorityInfo;
import zr.monitor.bean.info.ZRMachineInfo;
import zr.monitor.bean.info.ZRServerInfo;
import zr.monitor.bean.info.ZRServiceInfo;
import zr.monitor.cluster.ZRAbsCluster;

public class ZRClusterClient extends ZRAbsCluster {

	@Override
	protected void init0() {
	}

	@Override
	protected void destory0() {
	}

	public void setApiSettings(String methodName, boolean open, ZRAuthorityInfo[] authoritys) {

	}

	public void setApiVersionSettings(String methodName, String version, boolean open, ZRAuthorityInfo[] authoritys) {

	}

	public void openOrCloseMachine() {
	}

	public void openOrCloseServer() {
	}

	public void openOrCloseService() {
	}

	public List<ZRMachineInfo> getMachineInfos() {

	}

	public List<ZRServerInfo> getServerInfos() {

	}

	public List<ZRServiceInfo> getServerInfos() {

	}

	public List<ZRApiInfo> getApiInfos() {
	}

}
