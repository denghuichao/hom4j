package com.ctrip.tourtailor.framework.base;

import com.ctrip.tourtailor.framework.exception.HomException;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HAggregator {

    long count(byte[] startRow, byte[] endRow, Class<?> poType, Filter... filters)throws HomException;

    double sum(byte[] startRow, byte[] endRow, Class<?> poType, String columnName, Filter ... filters)throws HomException;
}
