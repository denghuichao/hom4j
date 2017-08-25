package com.ctrip.tourtailor.framework.hbase;

import com.ctrip.tourtailor.framework.base.HDataSourceAware;
import com.ctrip.tourtailor.framework.base.HSchema;
import com.ctrip.tourtailor.framework.exception.HOrmException;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HOrmAdmin implements HDataSourceAware, HSchema{

    @Override
    public void createTable(String tableName, String... columnFamilies) throws HOrmException {

    }

    @Override
    public HDataSource getHDataSource() {
        return null;
    }

    @Override
    public void setHDataSource(HDataSource dataSource) {

    }

    @Override
    public void deleteTable(String tableName) throws HOrmException {

    }
}
