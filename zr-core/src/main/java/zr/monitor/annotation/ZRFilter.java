package zr.monitor.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import zr.monitor.ZRRequestFilter;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ZRFilter {
	public Class<? extends ZRRequestFilter>[] value();
}
