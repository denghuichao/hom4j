package com.ctrip.tourtailor.framework.base;

import com.ctrip.tourtailor.framework.hbase.HDataSource;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HDataSourceAware {

    HDataSource getHDataSource();

    void setHDataSource(HDataSource dataSource);
}
