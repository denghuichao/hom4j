package com.dhc.github.framework.utils;

import com.dhc.github.framework.annotation.Table;
import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.conf.HTableDefinition;
import com.dhc.github.framework.exception.HomException;
import com.dhc.github.framework.exception.NotATableException;
import com.dhc.github.framework.exception.RowKeyNotDefineException;
import com.dhc.github.framework.parser.TypeParsers;
import com.dhc.github.framework.support.ClassScanner;
import com.dhc.github.framework.support.DefaultClassScanner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HBaseUtil {

    private static final ClassScanner classScanner = new DefaultClassScanner();

    private HBaseUtil() {
        throw new RuntimeException("util class can not be instantiated");
    }

    /**
     * Cache the HColumnDefinition of htables
     */
    private static Map<Class<?>, HTableDefinition> HCDS = new HashMap<>();

    static {
        List<Class<?>> entityClasses = classScanner.getClassListByAnnotation("", Table.class);
        for (Class<?> poType : entityClasses) {
            String tName = poType.getAnnotation(Table.class).value();
            List<HColumnDefinition> hcds = Lists.newArrayList();
            for (Field field : poType.getDeclaredFields()) {
                HColumnDefinition hcd = HColumnDefinition.parse(field);
                if (hcd != null) hcds.add(hcd);
            }
            HTableDefinition tableDefinition = new HTableDefinition();
            tableDefinition.setColumnDefinitions(hcds);
            tableDefinition.setPoType(poType);
            tableDefinition.setRowKey(getRowKeyInner(hcds));
            tableDefinition.setTableName(Strings.isNullOrEmpty(tName) ? poType.getName() : tName);
            HCDS.put(poType, tableDefinition);
        }
    }

    public static List<HColumnDefinition> getHColumnDefinitions(Class<?> poType) {
        if (!HCDS.containsKey(poType))
            throw new NotATableException(poType.getName() + " is not annotated as htable");

        return HCDS.get(poType).getColumnDefinitions();
    }

    public static <T> List<HColumnDefinition> getHColumnDefinitions(T po) {
        if (po != null)
            return getHColumnDefinitions(po.getClass());

        throw new NullPointerException("po must not be null");
    }

    private static HColumnDefinition getRowKeyInner(List<HColumnDefinition> hcds) {
        return hcds.stream().filter(e -> e.getIsRowkey()).findFirst().
                orElseThrow(() -> new RowKeyNotDefineException("rowkey is not defined"));
    }

    public static <T> HColumnDefinition getRowKey(T po) {
        if (po != null)
            return getRowKey(po.getClass());
        throw new NullPointerException("po must not be null");
    }

    public static HColumnDefinition getRowKey(Class<?> poType) {

        if (HCDS.containsKey(poType))
            return HCDS.get(poType).getRowKey();

        throw new NotATableException(poType.getName() + " is not annotated as htable");
    }

    public static <T> String getHTableName(Class<T> type) {
        if (HCDS.containsKey(type))
            return HCDS.get(type).getTableName();

        throw new NotATableException(type.getName() + " is not annotated as htable");
    }

    public static <T> String getHTableName(T po) {
        if (po != null)
            getHTableName(po.getClass());

        throw new NullPointerException("po must not be null");
    }

    public static <T> byte[] getRowKeyBytes(T po) {
        if (po != null) {
            HColumnDefinition rk = getRowKey(po);
            try {
                return TypeParsers.toBytes(rk.getField().get(po));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("po must not be null");
    }
}
