package zr.mybatis.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import v.common.unit.Ternary;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface KeyColumn {
	public Ternary sortAsc() default Ternary.UNKNOWN;
}
