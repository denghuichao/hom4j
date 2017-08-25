package com.ctrip.tourtailor.framework.hbase;

import com.ctrip.tourtailor.framework.base.HAggregator;
import com.ctrip.tourtailor.framework.base.HDataSourceAware;
import com.ctrip.tourtailor.framework.base.HPersistent;
import com.ctrip.tourtailor.framework.exception.HOrmException;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HOrmClient implements HDataSourceAware, HAggregator, HPersistent{

    @Override
    public HDataSource getHDataSource() {
        return null;
    }

    @Override
    public void setHDataSource(HDataSource dataSource) {

    }

    @Override
    public long count(byte[] startRow, byte[] endRow, Class<?> po) throws HOrmException {
        return 0;
    }

    @Override
    public <T> void put(T po) throws HOrmException {

    }

    @Override
    public long count(byte[] startRow, byte[] endRow, Class<?> po, Filter... filters) throws HOrmException {
        return 0;
    }

    @Override
    public <T> void putList(List<T> poList) throws HOrmException {

    }

    @Override
    public <T> T query(byte[] roeKey, Class<T> poType) throws HOrmException {
        return null;
    }

    @Override
    public double sum(byte[] startRow, byte[] endRow, Class<?> po, String columnName) throws HOrmException {
        return 0;
    }

    @Override
    public <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType) throws HOrmException {
        return null;
    }

    @Override
    public double sum(byte[] startRow, byte[] endRow, Class<?> po, String columnName, Filter... filters) throws HOrmException {
        return 0;
    }

    @Override
    public <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType) throws HOrmException {
        return null;
    }

    @Override
    public <T> HPager<T> queryByPage(HPager<T> HPager) throws HOrmException {
        return null;
    }

    @Override
    public <T> HPager<T> queryByPage(HPager<T> HPager, Filter... filters) throws HOrmException {
        return null;
    }

    @Override
    public void delete(byte[] rowKey, Class<?> poType) throws HOrmException {

    }

    @Override
    public void delete(List<byte[]> rowKeys, Class<?> poType) throws HOrmException {

    }

    @Override
    public <T> void delete(T po) throws HOrmException {

    }

    @Override
    public <T> void delete(List<T> poList) throws HOrmException {

    }
}
