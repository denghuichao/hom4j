package com.github.dhc.framework.hom.base;

import com.github.dhc.framework.hom.hbase.HDataSource;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HDataSourceAware {

    HDataSource getHDataSource();

    void setHDataSource(HDataSource dataSource);
}
