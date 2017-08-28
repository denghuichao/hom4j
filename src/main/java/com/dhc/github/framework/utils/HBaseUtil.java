package com.dhc.github.framework.utils;

import com.dhc.github.framework.annotation.Table;
import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.exception.HomException;
import com.dhc.github.framework.exception.NotATableException;
import com.dhc.github.framework.exception.RowKeyNotDefineException;
import com.dhc.github.framework.parser.TypeParsers;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HBaseUtil {

    private HBaseUtil() {
        throw new RuntimeException("util class can not be instantiated");
    }

    /**
     * Cache the HColumnDefinition of htables
     */
    private static Map<Class<?>, List<HColumnDefinition>> HCDS = new HashMap<>();

    public static List<HColumnDefinition> getHColumnDefinitions(Class<?> poType) {
        if (HCDS.containsKey(poType))
            return HCDS.get(poType);

        if (!poType.isAnnotationPresent(Table.class))
            throw new NotATableException(poType.getName() + " is not annotated as htable");

        List<HColumnDefinition> hcds = Lists.newArrayList();
        for (Field field : poType.getDeclaredFields()) {
            HColumnDefinition hcd = HColumnDefinition.parse(field);
            if (hcd != null) hcds.add(hcd);
        }

        HCDS.put(poType, hcds);

        return hcds;
    }

    public static <T> List<HColumnDefinition> getHColumnDefinitions(T po){
        if (po != null)
            return getHColumnDefinitions(po.getClass());

        throw new IllegalArgumentException("po must not be null");
    }

    public static HColumnDefinition getRowKey(List<HColumnDefinition> hcds) {
        return hcds.stream().filter(e -> e.getIsRowkey()).findFirst().
                orElseThrow(() -> new RowKeyNotDefineException("rowkey is not defined"));
    }

    public static <T> HColumnDefinition getRowKey(T po) {
        return getRowKey(getHColumnDefinitions(po));
    }

    public static HColumnDefinition getRowKey(Class<?> poType) {
        return getRowKey(getHColumnDefinitions(poType));
    }

    public static <T> String getHTableName(Class<T> type) {
        if (type.isAnnotationPresent(Table.class)) {
            Table annotation = type.getAnnotation(Table.class);
            String tname = annotation.value();
            return Strings.isNullOrEmpty(tname) ? type.getName() : tname;
        }
        throw new NotATableException(type.getName() + " is not annotated as an htable");
    }

    public static <T> String getHTableName(T po) {
        if (po != null)
            getHTableName(po.getClass());

        throw new HomException("po must not be null");
    }

    public static <T> byte[] getRowKeyBytes(T po) {
        if (po != null) {
            HColumnDefinition rk = getRowKey(po);
            if (rk != null) {
                try {
                    return TypeParsers.toBytes(rk.getField().get(po));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RowKeyNotDefineException("there is not rowkey defined in " + po.getClass());
        }
        throw new IllegalArgumentException("po must not be null");
    }
}
