package com.dhc.github.framework.parser;

import java.lang.reflect.ParameterizedType;

/**
 * 类型解析器解析抽象接口，用于抽象将byte[]和实际类型互转的操作
 */
public abstract class TypeParser<T> {

    protected abstract Class<?>[] getTypes();

    /**
     * 将byte[]转换成对象
     *
     * @param bytes
     * @return
     */
  protected abstract   T bytesToObject(byte[] bytes);

    /**
     * 将对象转换成byte[]
     *
     * @param object
     */
    protected abstract  byte[] objectToBytes(T object);

    public final T fromBytes(byte[] bytes) {
        if (canParseFromBytes(bytes))
            return bytesToObject(bytes);

        throw new IllegalArgumentException("cannot parse bytes to " + getGenericType());
    }

    public final byte[] toBytes(T object) {
        if (canParseToBytes(object))
            return objectToBytes(object);

        throw new IllegalArgumentException(object.getClass() + " doesn't match type " + getGenericType());
    }

    protected boolean canParseToBytes(Object o) {
        return getGenericType() == o.getClass();
    }

    protected boolean canParseFromBytes(byte[] bytes) {
        return true;
    }

    protected Class<?> getGenericType() {
        return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
