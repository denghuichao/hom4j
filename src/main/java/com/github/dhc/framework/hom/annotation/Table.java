package com.github.dhc.framework.hom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcdeng on 17-8-25.
 * annotate the name of the htable the po maps to
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * the htable name this po maps to
     * when name is not set with a not-empty value, the po maps to the htable
     * whose name equals to the class name
     */
    String value() default "";
}
