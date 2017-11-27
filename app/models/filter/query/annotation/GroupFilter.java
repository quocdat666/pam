package models.filter.query.annotation;

import models.filter.query.enu.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface GroupFilter {
    String name();

    Operator opatator() default Operator.AND;
}
