package com.ctrip.tourtailor.framework.base;

import com.ctrip.tourtailor.framework.exception.HomException;
import com.ctrip.tourtailor.framework.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HPersistent {

    <T> void putOne(T po)throws HomException;

    <T> void putList(List<T> poList) throws HomException;

    <T> T queryOne(byte[] roeKey, Class<T> poType)throws HomException;

    <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType) throws HomException;

    <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType) throws HomException;

    <T> HPager<T> queryByPage(HPager<T> HPager, Filter ... filters)throws HomException;

    void deleteOne(byte[] rowKey, Class<?> poType) throws HomException;

    void deleteList(List<byte[]> rowKeys, Class<?> poType) throws HomException;

    <T> void deleteOne(T po)throws HomException;

    <T> void deleteList(List<T> poList)throws HomException;
}
