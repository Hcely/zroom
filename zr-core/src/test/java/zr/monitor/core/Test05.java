package zr.monitor.core;

public class Test05 {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		System.out.println(A.class.getDeclaredMethod("test").isBridge());
		System.out.println(B.class.getDeclaredMethod("test").isBridge());
	}

	static class A {
		public void test() {
			
		}
	}

	static class B extends A {
		public void test() {
		}
	}
}
