package com.github.dhc.framework.hom.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@SuppressWarnings("rawtypes")
public class GenericUtil {

    public static Class<?> getGeneric(Class entityClass) {
        return getGeneric(entityClass, 1);
    }


    public static Class<?> getGeneric(Class entityClass, int index) {
        Type genType =  entityClass.getGenericSuperclass();

        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType)
                    .getActualTypeArguments();

            if ((params != null) && (params.length >= index)) {
                return (Class) params[index - 1];
            }
        }
        return Object.class;
    }
}
