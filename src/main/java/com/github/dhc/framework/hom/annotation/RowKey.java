package com.github.dhc.framework.hom.annotation;

import com.google.common.collect.Sets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;
import java.util.Set;

/**
 * Created by hcdeng on 17-8-25.
 * 功能描述：标注HBase表的rowkey
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowKey {

     //为了方便自动生成，限定rowkey类型如下
     Set<Class<?>> ALLOWED_ROWKEY_TYPES =
             Sets.newHashSet(int.class, Integer.class, long.class, Long.class, BigInteger.class, String.class);

    /**
     * 是否运行rowkey自动生成
     * @return
     */
     boolean auto() default false;
}
