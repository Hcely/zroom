package zr.monitor.cluster;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import v.common.helper.ParseUtil;
import v.common.helper.RandomHelper;
import v.common.unit.thread.VThreadLoop;
import zr.monitor.info.ZRInfoMgr;

public class ZRServerCluster extends ZRCluster {

	protected final ZRInfoMgr infoMgr;
	protected final VThreadLoop loop;

	public ZRServerCluster(ZRInfoMgr infoMgr, VThreadLoop loop) {
		this.infoMgr = infoMgr;
		this.loop = loop;
	}

	protected void init0() {
		zker.subscribeState(new ZRStatusListener());
		zker.subscribeChild(ZR_API_SETTINGS, new ZRApiSettingsListener());
		zker.subscribeChild(ZR_API_VERSION_SETTINGS, new ZRApiVersionSettingsListener());

		zker.subscribeData(ZR_MACHINE_INFO, infoMgr.getServerId(), new ZRMachineInfoListener());
		zker.subscribeData(ZR_MACHINE_SWITCH, infoMgr.getServerId(), new ZRMachineSwitchListener());

		zker.subscribeData(ZR_SERVER_INFO, infoMgr.getServerId(), new ZRServerInfoListener());
		zker.subscribeData(ZR_SERVER_SWITCH, infoMgr.getServerId(), new ZRServerSwitchListener());

		zker.subscribeData(ZR_SERVICE_SWITCH, infoMgr.getServerId(), new ZRServiceSwitchListener());
		zker.subscribeChild(ZR_PARAM, new ZRParamsListener());

		loop.execute(new InitializeTask(this));
	}

	@Override
	protected void destory0() {
	}

	private final class ZRStatusListener implements IZkStateListener {

		@Override
		public void handleStateChanged(KeeperState state) throws Exception {
			if (state == KeeperState.SyncConnected)
				loop.execute(new InitializeTask(ZRServerCluster.this));
		}

		@Override
		public void handleNewSession() throws Exception {
			loop.execute(new InitializeTask(ZRServerCluster.this));
		}
	}

	private final class ZRMachineInfoListener implements IZkDataListener {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			handleDataDeleted(dataPath);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
			loop.schedule(new LockMachineHandlerTask(ZRServerCluster.this), RandomHelper.randomInt(1000));
		}
	}

	private final class ZRMachineSwitchListener implements IZkDataListener {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			Boolean b = ParseUtil.parseBoolean(data.toString(), null);
			if (b != null)
				infoMgr.setMachineOpen(b);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {

		}
	}

	private final class ZRServerInfoListener implements IZkDataListener {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			handleDataDeleted(dataPath);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
			loop.execute(new LockServerHandlerTask(ZRServerCluster.this), RandomHelper.randomInt(1000));
		}
	}

	private final class ZRServerSwitchListener implements IZkDataListener {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			Boolean b = ParseUtil.parseBoolean(data.toString(), null);
			if (b != null)
				infoMgr.setServerOpen(b);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
		}
	}

	private final class ZRServiceSwitchListener implements IZkDataListener {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			Boolean b = ParseUtil.parseBoolean(data.toString(), null);
			if (b != null)
				infoMgr.setServiceOpen(b);
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
		}
	}

	private final class ZRApiSettingsListener implements IZkChildListener {
		@Override
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			loop.execute(new GetApiSettingsTask(ZRServerCluster.this));
		}
	}

	private final class ZRApiVersionSettingsListener implements IZkChildListener {
		@Override
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			loop.execute(new GetApiVersionSettingsTask(ZRServerCluster.this));
		}
	}

	private final class ZRParamsListener implements IZkChildListener {
		@Override
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			loop.execute(new GetParamsTask(ZRServerCluster.this));
		}
	}

}
