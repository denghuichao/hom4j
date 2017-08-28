package com.dhc.github.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcdeng on 17-8-25.
 * 功能描述：Hbase列映射注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 映射的Hbase列族
     */
    String family()  default "";

    /**
     * 映射的HBase列名
     */
    String name() default "";
}
