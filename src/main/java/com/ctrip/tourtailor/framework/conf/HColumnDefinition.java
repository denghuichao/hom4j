package com.ctrip.tourtailor.framework.conf;

import com.ctrip.tourtailor.framework.annotation.HColumn;
import com.ctrip.tourtailor.framework.annotation.RowKey;
import org.apache.directory.api.util.Strings;

import java.lang.reflect.Field;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HColumnDefinition {

    private String colummName;
    private String columnFamily;

    private String fieldName;
    private Class<?> fieldType;

    private boolean isRowkey;
    private long timestamp;

    public static HColumnDefinition parse(Field field){
        if(field.isAnnotationPresent(RowKey.class) || field.isAnnotationPresent(HColumn.class)) {
            HColumnDefinition hdc = new HColumnDefinition();
            hdc.setIsRowkey(isRowKey(field));
            String [] familyAndName = getFamilyAndColumnName(field);
            hdc.setColumnFamily(familyAndName[0]);
            hdc.setColummName(familyAndName[1]);
            hdc.setFieldName(field.getName());
            hdc.setFieldType(field.getType());
            return hdc;
        }
        return null;
    }

    private static boolean isRowKey(Field f){
        return f.isAnnotationPresent(RowKey.class);
    }

    private static String[] getFamilyAndColumnName(Field field){
        field.setAccessible(true);
        if(field.isAnnotationPresent(HColumn.class)) {
            HColumn hc = field.getAnnotation(HColumn.class);
            String family = hc.family();
            if(Strings.isEmpty(family))
                throw new IllegalArgumentException("family must not be empty");

            String name = hc.name();
            return new String[]{family, Strings.isEmpty(name) ? field.getName() : name};
        }else{
            return new String[]{field.getName(), field.getName()};
        }
    }

    public String getColummName() {
        return colummName;
    }

    public void setColummName(String colummName) {
        this.colummName = colummName;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean getIsRowkey() {
        return isRowkey;
    }

    public void setIsRowkey(boolean isRowkey) {
        this.isRowkey = isRowkey;
    }


    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }
}
