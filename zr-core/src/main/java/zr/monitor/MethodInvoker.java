package zr.monitor;

import java.lang.reflect.Method;

public interface MethodInvoker {
	public Object execute() throws Throwable;

	public Method getMethod();
}
