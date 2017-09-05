package com.github.dhc.framework.hom.hbase.criteria;

import com.github.dhc.framework.hom.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class QueryOperation<T> extends Operation<T> {

    private byte[] rowKey;

    private List<byte[]> rowKeys;

    private byte[] fromRow;

    private byte[] endRow;

    private HPager<T> pager;

    private Filter[] filters;

    public byte[] getRowKey() {
        return rowKey;
    }

    public void setRowKey(byte[] rowKey) {
        this.rowKey = rowKey;
    }

    public List<byte[]> getRowKeys() {
        return rowKeys;
    }

    public void setRowKeys(List<byte[]> rowKeys) {
        this.rowKeys = rowKeys;
    }

    public byte[] getFromRow() {
        return fromRow;
    }

    public void setFromRow(byte[] fromRow) {
        this.fromRow = fromRow;
    }

    public byte[] getEndRow() {
        return endRow;
    }

    public void setEndRow(byte[] endRow) {
        this.endRow = endRow;
    }

    public HPager<T> getPager() {
        return pager;
    }

    public void setPager(HPager<T> pager) {
        this.pager = pager;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

}
