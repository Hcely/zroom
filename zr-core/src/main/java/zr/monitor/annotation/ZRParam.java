package zr.monitor.annotation;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE_PARAMETER)
public @interface ZRParam {
	public String value() default "";

	public String demo() default "";
}
