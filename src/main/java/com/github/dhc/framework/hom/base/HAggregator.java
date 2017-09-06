package com.github.dhc.framework.hom.base;

import com.github.dhc.framework.hom.exception.HomException;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * Created by hcdeng on 17-8-25.
 * abstract the aggregation operations of hbase
 */
public interface HAggregator {

    /**
     * counting records of specific type with rowkey between startRow and endRow(exclusive)
     * @param startRow the least rowkey
     * @param endRow the largest rowkey(exclusive)
     * @param poType the po type
     * @param filters the hbase filters
     * @throws HomException
     * @return
     */
    long count(byte[] startRow, byte[] endRow, Class<?> poType, Filter... filters);

    long count(Object startRow, Object endRow, Class<?> poType, Filter... filters);

    /**
     * summing a specific filed with rowkey between startRow and endRow(exclusive)
     * @param startRow the least rowkey
     * @param endRow the largest rowkey(exclusive)
     * @param poType the po type
     * @param columnName the field to be calculated
     * @param filters the hbase filters
     * @throws HomException
     * @return
     */
    double sum(byte[] startRow, byte[] endRow, Class<?> poType, String columnName, Filter ... filters);

    double sum(Object startRow, Object endRow, Class<?> poType, String columnName, Filter ... filters);
}
