package com.github.dhc.framework.hom.base;

import com.github.dhc.framework.hom.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HPersistent {

    <T> Object putOne(T po);

    <T> List<Object> putList(List<T> poList);

    <T> T queryOne(byte[] rowKey, Class<T> poType);

    <T> T queryOne(Object rowKey, Class<T> poType);

    <T> T queryOne(Map<String, Object> kvs, Class<T> poType);

    <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType);

    <T> List<T> queryList_1(List<?> rowKeys, Class<T> poType);

    <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType);

    <T> List<T> queryList(Object fromRowKey, Object endRowKey, Class<T> poType);

    <T> List<T> queryList(Map<String, Object> kvs, Class<T> poType);

    <T> HPager<T> queryByPage(HPager<T> HPager, Filter ... filters);

    <T> void deleteOne(byte[] rowKey, Class<T> poType);

    <T> void deleteOne(Object rowKey, Class<T> poType);

    <T> void deleteList(List<byte[]> rowKeys, Class<T> poType);

    <T> void deleteList_1(List<Object> rowKeys, Class<T> poType);

    <T> void deleteOne(T po);

    <T> void deleteList(List<T> poList);
}
