package com.github.dhc.framework.hom.base;

import com.github.dhc.framework.hom.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HPersistent {

    /**
     * save a Persistent object into hbase table
     * @param po the object to save
     * @param <T>
     * @return the rowkey
     */
    <T> Object putOne(T po);

    /**
     * save a list of objects into hbase table
     * @param poList the objects to save
     * @param <T>
     * @return a list of rowkeys
     */
    <T> List<Object> putList(List<T> poList);

    /**
     * query a object of specific type by rowkey
     * @param rowKey the rowkey of queried record
     * @param poType the type of the record
     * @param <T>
     * @return a record or null if not found
     */
    <T> T queryOne(byte[] rowKey, Class<T> poType);

    /**
     * query a object of specific type by rowkey
     * @param rowKey the rowkey of queried record
     * @param poType the type of the record
     * @param <T>
     * @return a record or null if not found
     */
    <T> T queryOne(Object rowKey, Class<T> poType);

    /**
     * query a object of specific type by conditions
     * @param kvs k-v pairs of query parameters
     * @param poType the type of the record
     * @param <T>
     * @return a record or null if not found
     */
    <T> T queryOne(Map<String, Object> kvs, Class<T> poType);

    /**
     * query object of specific type by rowkeys
     * @param rowKeys the rowkeys of queried records
     * @param poType the type of the record
     * @param <T>
     * @return
     */
    <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType);

    /**
     * query object of specific type by rowkeys
     * @param rowKeys the rowkeys of queried records, should not be byte arrays here
     * @param poType the type of the record
     * @param <T>
     * @return
     */
    <T> List<T> queryList_1(List<?> rowKeys, Class<T> poType);

    /**
     * query objects of specific type with rowkey in range [fromRowKey, endRowKey)
     * @param fromRowKey the least rowkey
     * @param endRowKey the largest rowkey(exclusive)
     * @param poType the type of the record
     * @param <T>
     * @return
     */
    <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType);

    /**
     * query objects of specific type with rowkey in range [fromRowKey, endRowKey)
     * @param fromRowKey the least rowkey, should not be byte array here
     * @param endRowKey the largest rowkey(exclusive), should not be byte array here
     * @param poType the type of the record
     * @param <T>
     * @return
     */
    <T> List<T> queryList(Object fromRowKey, Object endRowKey, Class<T> poType);

    /**
     * query objects of specific type by conditions
     * @param kvs k-v pairs of query parameters
     * @param poType the type of the record
     * @param <T>
     * @return
     */
    <T> List<T> queryList(Map<String, Object> kvs, Class<T> poType);

    /**
     * query objects by page
     * @param HPager the page object
     * @param filters hbase filters
     * @param <T>
     * @return
     */
    <T> HPager<T> queryByPage(HPager<T> HPager, Filter ... filters);

    /**
     * delete one record of specific type by rowkey
     * @param rowKey the rowkey of the record
     * @param poType type of te record to be deleted
     * @param <T> the type parameter
     */
    <T> void deleteOne(byte[] rowKey, Class<T> poType);

    /**
     * delete one record of specific type by rowkey
     * @param rowKey the rowkey of the record, should not be byte array here
     * @param poType type of te record to be deleted
     * @param <T> the type parameter
     */
    <T> void deleteOne(Object rowKey, Class<T> poType);

    /**
     * delete records of specific type by rowkeys
     * @param rowKeys the rowkeys of the record
     * @param poType type of te record to be deleted
     * @param <T> the type parameter
     */
    <T> void deleteList(List<byte[]> rowKeys, Class<T> poType);

    /**
     * delete records of specific type by rowkeys
     * @param rowKeys the rowkeys of the record, should not be byte arrays here
     * @param poType type of te record to be deleted
     * @param <T> the type parameter
     */
    <T> void deleteList_1(List<Object> rowKeys, Class<T> poType);

    /**
     * delete one record of specific type whose rowkey equals to the rowkey of given po
     * @param po the given po only serves as the rowkey provider
     * @param <T>
     */
    <T> void deleteOne(T po);

    /**
     * delete records of specific type whose rowkey equals to the rowkey of given po
     * @param poList the given pos only serve as the rowkey provider
     * @param <T>
     */
    <T> void deleteList(List<T> poList);
}
