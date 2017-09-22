package zr.monitor.cluster;

import v.common.unit.VSimpleStatusObject;
import zr.common.zk.ZKER;

public abstract class ZRCluster extends VSimpleStatusObject {
	/**
	 * 系统参数信息
	 */
	protected static final String ZR_PARAM = "/zr/param";

	/**
	 * 记录api的权限配置
	 */
	protected static final String ZR_API_SETTINGS = "/zr/api/settings";
	/**
	 * 记录具体版本api信息
	 */
	protected static final String ZR_API_VERSION_INFO = "/zr/api/version";
	/**
	 * 记录具体版本api开关,权限
	 */
	protected static final String ZR_API_VERSION_SETTINGS = "/zr/api/version/settings";
	/**
	 * 记录服务器设备句柄
	 */
	protected static final String ZR_MACHINE_HANDLER = "/zr/machine/handler";
	/**
	 * 记录服务器设备状态信息
	 */
	protected static final String ZR_MACHINE_INFO = "/zr/machine/info";
	/**
	 * 记录服务器设备开关
	 */
	protected static final String ZR_MACHINE_SWITCH = "/zr/machine/switch";

	/**
	 * 记录服务器状态信息
	 */
	protected static final String ZR_SERVER_INFO = "/zr/server/info";
	/**
	 * 记录服务器开关
	 */
	protected static final String ZR_SERVER_SWITCH = "/zr/server/switch";

	/**
	 * 记录服务信息
	 */
	protected static final String ZR_SERVICE_INFO = "/zr/service/info";
	/**
	 * 记录服务开关
	 */
	protected static final String ZR_SERVICE_SWITCH = "/zr/service/switch";

	protected final ZKER zker;

	public ZRCluster() {
		this.zker = new ZKER();
	}

	public void setZkServers(String zkServers) {
		zker.setZkServers(zkServers);
	}

	public void setSessionTime(int sessionTime) {
		zker.setSessionTime(sessionTime);
	}

	@Override
	protected final void _init0() {
		zker.init();
		init0();
	}

	@Override
	protected final void _destory0() {
		zker.destory();
		destory0();
	}

	protected void init0() {
	}

	protected void destory0() {
	}

}
