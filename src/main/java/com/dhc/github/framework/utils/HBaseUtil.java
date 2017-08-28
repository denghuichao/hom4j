package com.dhc.github.framework.utils;

import com.dhc.github.framework.annotation.Table;
import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.parser.TypeParsers;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HBaseUtil {

    private HBaseUtil(){throw new InstantiationError("util class can not be instantiated");}

    /**
     * Cache the HColumnDefinition of htables
     */
    private static Map<Class<?>, List<HColumnDefinition>> HCDS = new HashMap<>();

    public static List<HColumnDefinition> getHColumnDefinitions(Class<?> poType) {
        if(HCDS.containsKey(poType))
            return HCDS.get(poType);

        List<HColumnDefinition> hcd = Arrays.stream(poType.getDeclaredFields()).map(f -> HColumnDefinition.parse(f))
                .filter(e -> e != null).collect(Collectors.toList());

        HCDS.put(poType, hcd);

        return hcd;
    }

    public static <T> List<HColumnDefinition> getHColumnDefinitions(T po) {
        if(po != null)
            return getHColumnDefinitions(po.getClass());

        throw new IllegalArgumentException("po must not be null");
    }

    public static HColumnDefinition getRowKey(List<HColumnDefinition> hcds) {
        return hcds.stream().filter(e -> e.getIsRowkey()).findFirst().orElse(null);
    }

    public static <T> HColumnDefinition getRowKey(T po) {
        return getRowKey(getHColumnDefinitions(po));
    }

    public static HColumnDefinition getRowKey(Class<?> poType) {
        return getRowKey(getHColumnDefinitions(poType));
    }

    public static <T> String getHTableName(Class<T> type){
        if(type.isAnnotationPresent(Table.class)){
            Table annotation = type.getAnnotation(Table.class);
            String tname = annotation.value();
            return Strings.isNullOrEmpty(tname) ? type.getName() : tname;
        }
        throw new IllegalArgumentException(type.getName() + " is not annotated as an htable");
    }

    public static <T> String getHTableName(T po){
        if(po != null)
            getHTableName(po.getClass());

        throw new IllegalArgumentException("po must not be null");
    }

    public static <T> byte[] getRowKeyBytes(T po) {
        if(po != null) {
            HColumnDefinition rk = getRowKey(po);
            if (rk != null) {
                try {
                    return TypeParsers.toBytes(rk.getField().get(po));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("there is not rowkey defined in " + po.getClass());
        }
        throw new IllegalArgumentException("po must not be null");
    }
}
