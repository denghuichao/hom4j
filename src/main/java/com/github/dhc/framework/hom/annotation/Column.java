package com.github.dhc.framework.hom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcdeng on 17-8-25.
 * Desc: this annotation maps the po(Persistent objects) fields to htable columns
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * the column family the field maps to
     * for every field which maps to htable, family must be set
     */
    String family()  default "";

    /**
     * the column ths field maps to
     * when name is not set with a not-empty value, the field maps to the column
     * whose name equals to the field name
     */
    String name() default "";
}
