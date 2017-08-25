package com.ctrip.tourtailor.framework.base;

import com.ctrip.tourtailor.framework.exception.HOrmException;
import com.ctrip.tourtailor.framework.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HPersistent {

    <T> void put(T po)throws HOrmException;

    <T> void putList(List<T> poList) throws HOrmException;

    <T> T query(byte[] roeKey, Class<T> poType)throws HOrmException;

    <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType) throws HOrmException;

    <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType) throws HOrmException;

    <T> HPager<T> queryByPage(HPager<T> HPager)throws HOrmException;

    <T> HPager<T> queryByPage(HPager<T> HPager, Filter ... filters)throws HOrmException;

    void delete(byte[] rowKey, Class<?> poType) throws HOrmException;

    void delete(List<byte[]> rowKeys, Class<?> poType) throws HOrmException;

    <T> void delete(T po)throws HOrmException;

    <T> void delete(List<T> poList)throws HOrmException;

}
