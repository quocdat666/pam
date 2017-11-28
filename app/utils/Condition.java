package utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
public @interface Condition {

    String columnName() default "";
    boolean ignoreCase() default false;
    Operator operator() default Operator.EQUAL;

}
