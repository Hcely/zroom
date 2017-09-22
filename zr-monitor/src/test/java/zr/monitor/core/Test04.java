package zr.monitor.core;

import org.I0Itec.zkclient.ZkClient;

public class Test04 {

	public static void main(String[] args) throws InterruptedException {
		ZkClient client = new ZkClient("127.0.0.1:2181");
		System.out.println(client);
		Thread.sleep(1000);
		System.out.println(client);
	}

}
