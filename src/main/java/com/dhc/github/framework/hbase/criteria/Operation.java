package com.dhc.github.framework.hbase.criteria;

import java.lang.reflect.ParameterizedType;

/**
 * Created by hcdeng on 17-8-25.
 */
abstract class Operation<T> {

   public final Class<T> getPoType(){
       return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
               .getActualTypeArguments()[0];
   }
}