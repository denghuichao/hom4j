package com.dhc.github.framework.base;

import com.dhc.github.framework.exception.HomException;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HAggregator {

    long count(byte[] startRow, byte[] endRow, Class<?> poType, Filter... filters);

    double sum(byte[] startRow, byte[] endRow, Class<?> poType, String columnName, Filter ... filters);
}
