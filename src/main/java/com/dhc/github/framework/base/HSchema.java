package com.dhc.github.framework.base;

import com.dhc.github.framework.exception.HomException;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HSchema {

    void createTable(String tableName, String...columnFamilies)throws HomException;

    void deleteTable(String tableName)throws HomException;

}
