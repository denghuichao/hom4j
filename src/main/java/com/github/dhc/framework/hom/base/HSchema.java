package com.github.dhc.framework.hom.base;

/**
 * Created by hcdeng on 17-8-25.
 */
public interface HSchema {

    /**
     * create a htable
     * @param tableName
     * @param columnFamilies
     */
    void createTable(String tableName, String...columnFamilies);

    /**
     * delete a htable if exist
     * @param tableName
     */
    void deleteTable(String tableName);
}
