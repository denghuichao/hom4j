package com.ctrip.tourtailor.framework.base;

import com.ctrip.tourtailor.framework.exception.HOrmException;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HSchema {

    void createTable(String tableName, String...columnFamilies)throws HOrmException;

    void deleteTable(String tableName)throws HOrmException;

}
