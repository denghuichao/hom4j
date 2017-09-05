package com.github.dhc.framework.hom.hbase.criteria;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class DeleteOperation<T> extends Operation<T> {

    private byte[] rowKey;

    private List<byte[]> rowKeys;

    private T po;

    private List<T> poList;

    public byte[] getRowKey() {
        return rowKey;
    }

    public void setRowKey(byte[] rowKey) {
        this.rowKey = rowKey;
    }

    public List<byte[]> getRowKeys() {
        return rowKeys;
    }

    public void setRowKeys(List<byte[]> rowKeys) {
        this.rowKeys = rowKeys;
    }

    public T getPo() {
        return po;
    }

    public void setPo(T po) {
        this.po = po;
    }

    public List<T> getPoList() {
        return poList;
    }

    public void setPoList(List<T> poList) {
        this.poList = poList;
    }

}
