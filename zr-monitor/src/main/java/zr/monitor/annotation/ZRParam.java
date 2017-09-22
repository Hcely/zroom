package zr.monitor.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import zr.monitor.bean.info.ZRParamInfo;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, TYPE_PARAMETER })
public @interface ZRParam {
	public String value() default "";

	public String demo() default "";

	public boolean required() default false;

	public int orgType() default ZRParamInfo.ORG_NORMAL;
}
