package com.ctrip.tourtailor.framework.utils;

import com.ctrip.tourtailor.framework.conf.HColumnDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HBaseUtil {

    public static List<HColumnDefinition> getHColumnDefinitions(Class<?> poType){
        return Arrays.stream(poType.getDeclaredFields()).map(f -> HColumnDefinition.parse(f))
                .filter(e -> e != null).collect(Collectors.toList());
    }

    public static <T> List<HColumnDefinition> getHColumnDefinitions(T po){
        return getHColumnDefinitions(po.getClass());
    }

    public static HColumnDefinition getRowKey(List<HColumnDefinition> hcds){
        return hcds.stream().filter(e -> e.getIsRowkey()).findFirst().orElse(null);
    }

    public static <T> HColumnDefinition getRowKey(T po){
        return getRowKey(getHColumnDefinitions(po));
    }

    public static HColumnDefinition getRowKey(Class<?> poType){
        return getRowKey(getHColumnDefinitions(poType));
    }


}
