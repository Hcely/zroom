package zr.monitor.cluster;

import v.Initializable;
import v.VObject;
import zr.monitor.util.ZKER;

public abstract class ZRCluster implements VObject, Initializable {
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
	protected boolean init;
	protected boolean destory;

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
	public final void init() {
		if (init)
			return;
		synchronized (this) {
			if (init)
				return;
			init = true;
			zker.init();
		}
		init0();
	}

	protected abstract void init0();

	@Override
	public final void destory() {
		if (destory)
			return;
		synchronized (this) {
			if (destory)
				return;
			if (!init)
				return;
			destory = true;
		}
		zker.destory();
		destory0();
	}

	protected abstract void destory0();

	@Override
	public final boolean isInit() {
		return init;
	}

	@Override
	public final boolean isDestory() {
		return destory;
	}

}
