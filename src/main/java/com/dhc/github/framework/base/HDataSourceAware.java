package com.dhc.github.framework.base;

import com.dhc.github.framework.hbase.HDataSource;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HDataSourceAware {

    HDataSource getHDataSource();

    void setHDataSource(HDataSource dataSource);
}
