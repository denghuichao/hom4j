package com.github.dhc.framework.hom.hbase.criteria;

import org.apache.hadoop.hbase.filter.Filter;

/**
 * Created by hcdeng on 17-8-25.
 */
public class AggregationOperation<T> extends Operation<T> {

    private byte[] startRow;

    private byte[] endRow;

    private Filter[] filters;

    private String cloumnName;

    public byte[] getStartRow() {
        return startRow;
    }

    public void setStartRow(byte[] startRow) {
        this.startRow = startRow;
    }

    public byte[] getEndRow() {
        return endRow;
    }

    public void setEndRow(byte[] endRow) {
        this.endRow = endRow;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    public String getCloumnName() {
        return cloumnName;
    }

    public void setCloumnName(String cloumnName) {
        this.cloumnName = cloumnName;
    }

}
