/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-29
 * Project        : horm
 * File Name      : HTableDefinition.java
 */
package com.github.dhc.framework.hom.hbase;

import com.github.dhc.framework.hom.exception.HomException;
import com.github.dhc.framework.hom.utils.GenericUtil;
import java.util.List;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-29
 * @since hom 1.0
 */
public class HTableDefinition {

    private Class<?> poType;

    private String tableName;

    private HColumnDefinition rowKey;

    private RowkeyProvider<?, ?> rowkeyProvider;

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

    public RowkeyProvider<?, ?> getRowkeyProvider() {
        return rowkeyProvider;
    }

    public void setRowkeyProvider(Class<? extends RowkeyProvider<?, ?>> clazz) {
        if(clazz == null) return;

        Class<?> poType_1 = GenericUtil.getGeneric(clazz, 1);
        Class<?> keyType_1 = GenericUtil.getGeneric(clazz, 2);
        if(poType != poType_1 ||
                keyType_1 != rowKey.getFieldType()){
            throw new HomException(String.format("the type params of rowkeyProvider should be <%s, %s>",
                    poType.getName(),
                    rowKey.getFieldType().getCanonicalName()));
        }

        try {
            this.rowkeyProvider = clazz.newInstance();
        }catch (Exception e){
            throw new HomException(e);
        }
    }
}
