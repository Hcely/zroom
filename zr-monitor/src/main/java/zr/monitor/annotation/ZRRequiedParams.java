package zr.monitor.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ZRRequiedParams {
	/**
	 * type:xxx|name:xxx|org:xxx|req:xxx|desc:xxxx|demo:xxx
	 */
	public String[] value();
}
