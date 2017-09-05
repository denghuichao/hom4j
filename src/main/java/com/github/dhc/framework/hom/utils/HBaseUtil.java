package com.github.dhc.framework.hom.utils;

import com.github.dhc.framework.hom.annotation.RowKey;
import com.github.dhc.framework.hom.annotation.Table;
import com.github.dhc.framework.hom.hbase.HColumnDefinition;
import com.github.dhc.framework.hom.hbase.HTableDefinition;
import com.github.dhc.framework.hom.exception.NotATableException;
import com.github.dhc.framework.hom.exception.RowKeyNotDefineException;
import com.github.dhc.framework.hom.utils.parser.TypeParsers;
import com.google.common.base.Strings;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hcdeng on 17-8-25.
 */
public final class HBaseUtil {

    private HBaseUtil() {
        throw new RuntimeException("util class can not be instantiated");
    }

    /**
     * Cache the HColumnDefinition of htables
     */
    private static Map<Class<?>, HTableDefinition> HCDS = new HashMap<>();

    public static List<HColumnDefinition> getHColumnDefinitions(Class<?> poType) {
        if (!HCDS.containsKey(poType))
            loadTableDefinition(poType);

        return HCDS.get(poType).getColumnDefinitions();
    }

    private static HTableDefinition loadTableDefinition(Class<?> poType) {
        if (!poType.isAnnotationPresent(Table.class))
            throw new NotATableException(poType.getName() + " is not annotated as htable");

        String tName = poType.getAnnotation(Table.class).value();
        List<HColumnDefinition> hcds = Stream.of(poType.getDeclaredFields()).map(f -> HColumnDefinition.parse(f))
                .filter(hcd -> hcd != null).collect(Collectors.toList());

        HTableDefinition tableDefinition = new HTableDefinition();
        tableDefinition.setColumnDefinitions(hcds);
        tableDefinition.setPoType(poType);
        tableDefinition.setRowKey(getRowKeyInner(hcds));
        tableDefinition.setTableName(Strings.isNullOrEmpty(tName) ? poType.getName() : tName);
        HCDS.put(poType, tableDefinition);

        return tableDefinition;
    }

    public static <T> List<HColumnDefinition> getHColumnDefinitions(T po) {
        if (po != null)
            return getHColumnDefinitions(po.getClass());

        throw new NullPointerException("po must not be null");
    }

    private static HColumnDefinition getRowKeyInner(List<HColumnDefinition> hcds) {
        HColumnDefinition rowkey = hcds.stream().filter(e -> e.getIsRowkey()).findFirst().
                orElseThrow(() -> new RowKeyNotDefineException("rowkey is not defined"));

        if (!RowKey.ALLOWED_ROWKEY_TYPES.contains(rowkey.getFieldType())) {
            throw new RowKeyNotDefineException("rowtype must in types: " +
                    RowKey.ALLOWED_ROWKEY_TYPES.stream().map(e -> e.getName()).collect(Collectors.toList()));
        }

        return rowkey;
    }

    public static <T> HColumnDefinition getRowKey(T po) {
        if (po != null)
            return getRowKey(po.getClass());

        throw new NullPointerException("po must not be null");
    }

    public static HColumnDefinition getRowKey(Class<?> poType) {

        if (!HCDS.containsKey(poType))
            loadTableDefinition(poType);

        if (HCDS.containsKey(poType))
            return HCDS.get(poType).getRowKey();

        throw new NotATableException(poType.getName() + " is not annotated as htable");
    }

    public static <T> String getHTableName(Class<T> type) {
        if (!HCDS.containsKey(type))
            loadTableDefinition(type);

        if (HCDS.containsKey(type))
            return HCDS.get(type).getTableName();

        throw new NotATableException(type.getName() + " is not annotated as htable");
    }

    public static <T> String getHTableName(T po) {
        if (po != null)
            return getHTableName(po.getClass());

        throw new NullPointerException("po must not be null");
    }

    public static <T> byte[] getRowKeyBytes(T po) {
        if (po != null) {
            HColumnDefinition rk = getRowKey(po);
            try {
                Object o = rk.getField().get(po);

                if (o == null && rk.getField().getAnnotation(RowKey.class).auto()) {
                    o = autoGenerateRowKey(rk);
                }

                if (o == null) throw new NullPointerException("rowkey must not be null");

                return TypeParsers.toBytes(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("po must not be null");
    }

    private static Object autoGenerateRowKey(HColumnDefinition rk) {
        if (!rk.getIsRowkey())
            throw new IllegalArgumentException("not annotated as rowkey");

        Class type = rk.getFieldType();
        if (!RowKey.ALLOWED_ROWKEY_TYPES.contains(rk.getFieldType())) {
            throw new RowKeyNotDefineException("rowtype must in types: " +
                    RowKey.ALLOWED_ROWKEY_TYPES.stream().map(e -> e.getName()).collect(Collectors.toList()));
        }

        if (String.class == type) {
            return UUID.randomUUID().toString();
        } else if (int.class == type || Integer.class == type) {
            return System.currentTimeMillis() % Integer.MAX_VALUE;
        } else if (long.class == type || Long.class == type) {
            return System.currentTimeMillis();
        } else {
            return BigInteger.valueOf(System.currentTimeMillis());
        }
    }

    public static  <T> byte[] objectToRowkey(Object rowKey, Class<T> poType){
        HColumnDefinition rowKeyHcd = getRowKey(poType);
        if(rowKey.getClass() != rowKeyHcd.getFieldType()) {
            throw new IllegalArgumentException("rowKey should be " +
                    rowKeyHcd.getFieldType().getName() + ", " +
                    "but actual is " + rowKey.getClass().getName());
        }
        return TypeParsers.toBytes(rowKey);
    }

    public static  <T> List<byte[]> objectsToRowkeys(List<?> rowKeys, Class<T> poType){
        return rowKeys.stream().map(e -> objectToRowkey(e, poType)).collect(Collectors.toList());
    }
}
