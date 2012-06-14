package coffee.sqlite.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface Column {

    String name() default "";

    boolean nullable() default true;

    int length() default 255;
}