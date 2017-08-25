package com.ctrip.tourtailor.framework.hbase.criteria;

import com.ctrip.tourtailor.framework.base.HAggregator;
import com.ctrip.tourtailor.framework.base.HPersistent;
import com.ctrip.tourtailor.framework.exception.HOrmException;
import com.ctrip.tourtailor.framework.hbase.HPager;
import org.apache.directory.api.util.Strings;
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

    public static <T> PutCriteriaBuilder<T> put(Class<T> poType) {
        return new PutCriteriaBuilder<>();
    }

    public static <T> QueryCriteriaBuilder<T> find(Class<T> poType) {
        return new QueryCriteriaBuilder<>();
    }

    public static <T> DeleteCriteriaBuilder<T> delete(Class<T> poType) {
        return new DeleteCriteriaBuilder<>();
    }

    public static <T> AggregationCriteriaBuilder<T> aggregate(Class<T> poType) {
        return new AggregationCriteriaBuilder<>();
    }

    public void execute(HPersistent hPersistent) throws HOrmException {

        if (operation instanceof PutOperation) {
            doPutOperation(hPersistent);
        } else if (operation instanceof DeleteOperation) {
            doDeleteOperation(hPersistent);
        } else {
            throw new HOrmException("operation " + operation.getClass().getName() +
                    "does not match return type void");
        }
    }

    private void doDeleteOperation(HPersistent hPersistent) throws HOrmException {
        DeleteOperation<?> deleteOperation = (DeleteOperation<?>) operation;
        if (deleteOperation.getRowKey() != null) {
            hPersistent.delete(deleteOperation.getRowKey(), deleteOperation.getPoType());
        } else if (deleteOperation.getRowKeys() != null) {
            hPersistent.delete(deleteOperation.getRowKeys(), deleteOperation.getPoType());
        } else if (deleteOperation.getPo() != null) {
            hPersistent.delete(deleteOperation.getPo());
        } else if (deleteOperation.getPoList() != null) {
            hPersistent.delete(deleteOperation.getPoList());
        } else {
            throw new HOrmException("delete operation is not correctly built");
        }
    }

    private void doPutOperation(HPersistent hPersistent) throws HOrmException {
        PutOperation<?> putOperation = (PutOperation<?>) operation;
        if (putOperation.getPo() != null) {
            hPersistent.put(putOperation.getPo());
        } else if (putOperation.getPoList() != null) {
            hPersistent.put(putOperation.getPoList());
        } else {
            throw new HOrmException("query operation is not correctly built");
        }
    }

    public long count(HAggregator hAggregator) throws HOrmException {
        if (operation instanceof AggregationOperation) {
            AggregationOperation<?> op = (AggregationOperation<?>) operation;
            if (op.getStartRow() != null && op.getEndRow() != null) {
                return hAggregator.count(op.getStartRow(), op.getEndRow(), op.getPoType(), op.getFilters());
            }

            throw new HOrmException("aggregation operation is not correctly built");
        }
        throw new HOrmException("operation does not match");
    }

    public double sum(HAggregator hAggregator) throws HOrmException {
        if (operation instanceof AggregationOperation) {
            AggregationOperation<?> op = (AggregationOperation<?>) operation;
            if (op.getStartRow() != null && op.getEndRow() != null
                    && Strings.isNotEmpty(op.getCloumnName())) {
                return hAggregator.sum(op.getStartRow(), op.getEndRow(), op.getPoType(),
                        op.getCloumnName(), op.getFilters());
            }
            throw new HOrmException("aggregation operation is not correctly built");
        }
        throw new HOrmException("operation does not match");
    }

    public <T> T query(HPersistent hPersistent) throws HOrmException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getRowKey() == null) {
                throw new HOrmException("rowkey must be set for this operation");
            }
            return hPersistent.query(op.getRowKey(), op.getPoType());
        }
        throw new HOrmException("operation does not match");
    }

    public <T> List<T> queryList(HPersistent hPersistent) throws HOrmException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getRowKeys() != null) {
                return hPersistent.queryList(op.getRowKeys(), op.getPoType());
            } else if (op.getFromRow() != null && op.getEndRow() != null) {
                return hPersistent.queryList(op.getFromRow(), op.getEndRow(), op.getPoType());
            }

            throw new HOrmException("query operation is not correctly built");
        }
        throw new HOrmException("operation does not match");
    }

    public <T> HPager<T> queryByPage(HPersistent hPersistent) throws HOrmException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getPager() != null) {
                return hPersistent.queryByPage(op.getPager(), op.getFilters());
            }

            throw new HOrmException("pager must be set for this operation");
        }
        throw new HOrmException("operation does not match");
    }

    public static class PutCriteriaBuilder<T> {

        private PutOperation<T> putOperation;

        public PutCriteriaBuilder() {
            putOperation = new PutOperation<>();
        }

        public PutCriteriaBuilder putObject(T t) {
            putOperation.setPo(t);
            return this;
        }

        public PutCriteriaBuilder putList(List<T> poList) {
            putOperation.setPoList(poList);
            return this;
        }

        public HCriteria build() {
            return new HCriteria(putOperation);
        }
    }

    public static class QueryCriteriaBuilder<T> {

        private QueryOperation<T> queryOperation;

        public QueryCriteriaBuilder() {
            queryOperation = new QueryOperation<>();
        }

        public QueryCriteriaBuilder byRowKey(byte[] rowKey) {
            queryOperation.setRowKey(rowKey);
            return this;
        }

        public QueryCriteriaBuilder byRowKeys(List<byte[]> rowKeys) {
            queryOperation.setRowKeys(rowKeys);
            return this;
        }

        public QueryCriteriaBuilder fromRow(byte[] fromRow) {
            queryOperation.setFromRow(fromRow);
            return this;
        }

        public QueryCriteriaBuilder toRow(byte[] endRow) {
            queryOperation.setEndRow(endRow);
            return this;
        }

        public QueryCriteriaBuilder withFilters(Filter... filters) {
            queryOperation.setFilters(filters);
            return this;
        }

        public QueryCriteriaBuilder byPage(HPager<T> pager) {
            queryOperation.setPager(pager);
            return this;
        }

        public HCriteria build() {
            return new HCriteria(queryOperation);
        }
    }

    public static class DeleteCriteriaBuilder<T> {
        private DeleteOperation<T> deleteOperation;

        public DeleteCriteriaBuilder() {
            this.deleteOperation = new DeleteOperation<>();
        }

        public DeleteCriteriaBuilder byRowKey(byte[] rowKey) {
            deleteOperation.setRowKey(rowKey);
            return this;
        }

        public DeleteCriteriaBuilder byRowKeys(List<byte[]> rowKeys) {
            deleteOperation.setRowKeys(rowKeys);
            return this;
        }

        public DeleteCriteriaBuilder byPo(T po) {
            deleteOperation.setPo(po);
            return this;
        }

        public DeleteCriteriaBuilder byPos(List<T> pos) {
            deleteOperation.setPoList(pos);
            return this;
        }

        public HCriteria build() {
            return new HCriteria(deleteOperation);
        }
    }

    public static class AggregationCriteriaBuilder<T> {
        private AggregationOperation<T> aggregationOperation;

        public AggregationCriteriaBuilder() {
            aggregationOperation = new AggregationOperation<>();
        }

        public AggregationCriteriaBuilder fromRow(byte[] startRow) {
            aggregationOperation.setStartRow(startRow);
            return this;
        }

        public AggregationCriteriaBuilder toRow(byte[] endRow) {
            aggregationOperation.setEndRow(endRow);
            return this;
        }

        public AggregationCriteriaBuilder byFilters(Filter... filters) {
            aggregationOperation.setFilters(filters);
            return this;
        }

        public AggregationCriteriaBuilder byCloumn(String columnName) {
            aggregationOperation.setCloumnName(columnName);
            return this;
        }
    }
}
