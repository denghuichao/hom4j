package com.dhc.github.framework.utils;

import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.parser.TypeParsers;
import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.List;

/**
 * Created by hcdeng on 17-8-28.
 * desc: 从HBASE返回的结果中提取对象
 */
public class PoExtractor {

    private PoExtractor(){throw new InstantiationError("util class can not be instantiated");}


    public static <T> T extract(Result result, Class<T> poType) {
        return extract(new Result[]{result}, poType).get(0);
    }

    public static <T> List<T> extract(Result[] results, Class<T> poType) {
        List<HColumnDefinition> hcds = HBaseUtil.getHColumnDefinitions(poType);
        HColumnDefinition rowkey = HBaseUtil.getRowKey(hcds);
        List<T> res = Lists.newArrayList();
        for(Result result: results) {
            try {
                T t = poType.newInstance();
                rowkey.getField().set(t, TypeParsers.fromBytes(rowkey.getFieldType(), result.getRow()));
                for (HColumnDefinition hcd : hcds) {
                    Cell cell = result.getColumnLatestCell(TypeParsers.toBytes(hcd.getColumnFamily()),
                            TypeParsers.toBytes(hcd.getColummName()));
                    hcd.getField().set(t, TypeParsers.fromBytes(hcd.getFieldType(), CellUtil.cloneValue(cell)));
                }
                res.add(t);
            }catch (Throwable e){
                throw new RuntimeException(e);
            }
        }
        return res;
    }

}
