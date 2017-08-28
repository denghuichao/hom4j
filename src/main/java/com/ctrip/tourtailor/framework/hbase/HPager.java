package com.ctrip.tourtailor.framework.hbase;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HPager<T> {

    private byte[] startRow;
    private byte[] stopRow;

    private int pageNumber;     // 页面编号
    private int pageSize;       // 每页条数
    private List<T> recordList; // 数据列表

    public HPager() {
    }

    /**
     * 该构造函数用来构造一个分页查询的请求
     * @param startRow
     * @param stopRow
     * @param pageSize
     */
    public HPager(byte[] startRow, byte[] stopRow, int pageSize) {
        this.startRow = startRow;
        this.stopRow = stopRow;
        this.pageSize = pageSize;
    }

    /**
     * 该构造函数用来构造一个分页的结果，包含了数据集
     * @param pageNumber
     * @param pageSize
     * @param totalRecord
     * @param recordList
     */
    public HPager(int pageNumber, int pageSize, long totalRecord, List<T> recordList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.recordList = recordList;
    }

    protected Class<?> getGenericType() {
        return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setStartRow(byte[] startRow) {
        this.startRow = startRow;
    }

    public void setStopRow(byte[] stopRow) {
        this.stopRow = stopRow;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public byte[] getStartRow() {
        return startRow;
    }

    public byte[] getStopRow() {
        return stopRow;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }


    public List<T> getRecordList() {
        return recordList;
    }

    public boolean isFirstPage() {
        return pageNumber == 0;
    }

}
