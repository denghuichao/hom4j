package com.github.dhc.framework.hom.utils;

import com.github.dhc.framework.hom.utils.parser.TypeParsers;
import com.github.dhc.framework.hom.hbase.HColumnDefinition;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.List;

/**
 * Created by hcdeng on 17-8-28.
 * desc: 从HBASE返回的结果中提取对象
 */
public final class PoExtractor {

    private PoExtractor() {
        throw new RuntimeException("util class can not be instantiated");
    }


    public static <T> T extract(Result result, Class<T> poType) {
        return extract(new Result[]{result}, poType).stream().findFirst().orElse(null);
    }

    public static <T> List<T> extract(Result[] results, Class<T> poType) {
        List<T> res = Lists.newArrayList();
        try {
            List<HColumnDefinition> hcds = HBaseUtil.getHColumnDefinitions(poType);
            HColumnDefinition rowkey = HBaseUtil.getRowKey(poType);

            for (Result result : results) {
                if(result.isEmpty())continue;
                T t = poType.newInstance();
                rowkey.getField().set(t, TypeParsers.fromBytes(rowkey.getFieldType(), result.getRow()));
                for (HColumnDefinition hcd : hcds) {
                    if(!hcd.getIsRowkey()) {
                        Cell cell = result.getColumnLatestCell(TypeParsers.toBytes(hcd.getColumnFamily()),
                                TypeParsers.toBytes(hcd.getColummName()));
                        hcd.getField().set(t, TypeParsers.fromBytes(hcd.getFieldType(), CellUtil.cloneValue(cell)));
                    }
                }
                res.add(t);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
