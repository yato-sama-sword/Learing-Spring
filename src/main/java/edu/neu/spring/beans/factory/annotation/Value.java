package edu.neu.spring.beans.factory.annotation;

import java.lang.annotation.*;


/**
 * @author yato
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
