package zr.monitor.core;

import java.io.IOException;

import zr.monitor.util.ZRMonitorUtil;

public class AppTest {
	public static void main(String[] args) {
		E e = new E();
		e.error = new IOException("aaaaaa");
		String str = ZRMonitorUtil.objToJson(e);
		System.out.println(str);
		System.out.println(ZRMonitorUtil.jsonToObj(str, E.class));
	}

	public static class E {
		public IOException error;

		public IOException getError() {
			return error;
		}

		public void setError(IOException error) {
			this.error = error;
		}

		@Override
		public String toString() {
			error.printStackTrace();
			return "E [error=" + error + "]";
		}

	}
}
