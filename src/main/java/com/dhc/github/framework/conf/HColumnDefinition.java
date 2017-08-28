package com.dhc.github.framework.conf;

import com.dhc.github.framework.annotation.Column;
import com.dhc.github.framework.annotation.RowKey;
import com.dhc.github.framework.exception.HomException;
import org.apache.directory.api.util.Strings;

import java.lang.reflect.Field;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HColumnDefinition {

    private String colummName;
    private String columnFamily;

    private Field field;
    private Class<?> fieldType;

    private boolean isRowkey;
    private long timestamp;

    public static HColumnDefinition parse(Field field)throws HomException{
        if(field.isAnnotationPresent(RowKey.class) || field.isAnnotationPresent(Column.class)) {
            HColumnDefinition hdc = new HColumnDefinition();
            hdc.setIsRowkey(isRowKey(field));
            String [] familyAndName = getFamilyAndColumnName(field);
            hdc.setColumnFamily(familyAndName[0]);
            hdc.setColummName(familyAndName[1]);
            hdc.setField(field);
            hdc.setFieldType(field.getType());
            return hdc;
        }
        return null;
    }

    private static boolean isRowKey(Field f){
        return f.isAnnotationPresent(RowKey.class);
    }

    private static String[] getFamilyAndColumnName(Field field)throws HomException{
        field.setAccessible(true);
        if(field.isAnnotationPresent(Column.class)) {
            Column hc = field.getAnnotation(Column.class);
            String family = hc.family();
            if(Strings.isEmpty(family))
                throw new HomException("family must not be empty");

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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
