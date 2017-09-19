package zr.mybatis.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import v.common.unit.Ternary;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, TYPE })
public @interface MapperConfig {
	public String template() default "";

	public String table() default "";

	public Ternary ignoreEmpty() default Ternary.UNKNOWN;

	public Ternary insertAsMap() default Ternary.UNKNOWN;

	public String[] fields() default { "*" };
}
