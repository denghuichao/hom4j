package com.dhc.github.framework.base;

import com.dhc.github.framework.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HPersistent {

    <T> void putOne(T po);

    <T> void putList(List<T> poList);

    <T> T queryOne(byte[] roeKey, Class<T> poType);

    <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType);

    <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType);

    <T> HPager<T> queryByPage(HPager<T> HPager, Filter ... filters);

    void deleteOne(byte[] rowKey, Class<?> poType);

    void deleteList(List<byte[]> rowKeys, Class<?> poType);

    <T> void deleteOne(T po);

    <T> void deleteList(List<T> poList);
}
