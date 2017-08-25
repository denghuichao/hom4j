package com.ctrip.tourtailor.framework.hbase;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HPager<T> {

    private byte[] startRow;
    private byte[] stopRow;

    private int pageNumber;     // 页面编号
    private int pageSize;       // 每页条数
    private long totalRecord;   // 总记录数
    private long totalPage;     // 总页面数
    private List<T> recordList; // 数据列表

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
        this.totalRecord = totalRecord;
        this.recordList = recordList;
        if (pageSize != 0) {
            totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        }
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

    public long getTotalRecord() {
        return totalRecord;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public boolean isFirstPage() {
        return pageNumber == 0;
    }

    public boolean isLastPage() {
        return pageNumber == totalPage - 1;
    }

    public boolean hasPrevPage() {
        return pageNumber > 0 && pageNumber < totalPage;
    }

    public boolean hasNextPage() {
        return pageNumber < totalPage - 1;
    }
}
