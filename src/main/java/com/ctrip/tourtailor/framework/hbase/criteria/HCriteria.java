package com.ctrip.tourtailor.framework.hbase.criteria;

import com.ctrip.tourtailor.framework.hbase.HPager;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HCriteria {

    private Operation<?> operation;

    public HCriteria(Operation<?> operation) {
        this.operation = operation;
    }

    public static class PutCriteriaBuilder<T>{

        private PutOperation<T> putOperation;

        public PutCriteriaBuilder() {
            putOperation = new PutOperation<>();
        }

        public PutCriteriaBuilder putObject(T t){
            putOperation.setPo(t);
            return this;
        }

        public PutCriteriaBuilder putList(List<T> poList){
            putOperation.setPoList(poList);
            return this;
        }

        public HCriteria build(){
            return new HCriteria(putOperation);
        }
    }

    public static class QueryCriteriaBuilder<T>{

        private QueryOperation<T> queryOperation;

        public QueryCriteriaBuilder() {
            queryOperation = new QueryOperation<>();
        }

        public QueryCriteriaBuilder byRowKey(byte[] rowKey){
            queryOperation.setRowKey(rowKey);
            return this;
        }

        public QueryCriteriaBuilder byRowKeys(List<byte[]>rowKeys){
            queryOperation.setRowKeys(rowKeys);
            return this;
        }

        public QueryCriteriaBuilder fromRow(byte[] fromRow){
            queryOperation.setFromRow(fromRow);
            return this;
        }

        public QueryCriteriaBuilder toRow(byte[] endRow){
            queryOperation.setEndRow(endRow);
            return this;
        }

        public QueryCriteriaBuilder withFilters(Filter... filters){
            queryOperation.setFilters(filters);
            return this;
        }

        public QueryCriteriaBuilder byPage(HPager<T> pager){
            queryOperation.setPager(pager);
            return this;
        }

        public HCriteria build(){
            return new HCriteria(queryOperation);
        }
    }

    public static class DeleteCriteriaBuilder<T>{
        private DeleteOperation<T> deleteOperation;

        public DeleteCriteriaBuilder() {
            this.deleteOperation = new DeleteOperation<>();
        }

//        private byte[] rowKey;
//
//        private List<byte[]> rowKeys;
//
//        private T po;
//
//        private List<T> poList;

    }
}
