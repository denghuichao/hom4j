package com.dhc.github.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcdeng on 17-8-25.
 * 功能: HBASE表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 映射的Hbase表名
     */
    String value() default "";
}
