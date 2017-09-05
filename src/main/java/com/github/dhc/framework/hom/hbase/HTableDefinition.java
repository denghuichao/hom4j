/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-29
 * Project        : horm
 * File Name      : HTableDefinition.java
 */
package com.github.dhc.framework.hom.hbase;

import java.util.List;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-29
 * @since horm 1.0
 */
public class HTableDefinition {
    private Class<?> poType;
    private String tableName;

    private HColumnDefinition rowKey;

    List<HColumnDefinition> columnDefinitions;

    public Class<?> getPoType() {
        return poType;
    }

    public void setPoType(Class<?> poType) {
        this.poType = poType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public HColumnDefinition getRowKey() {
        return rowKey;
    }

    public void setRowKey(HColumnDefinition rowKey) {
        this.rowKey = rowKey;
    }

    public List<HColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<HColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }
}
