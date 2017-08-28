package com.dhc.github.framework.hbase.criteria;

import com.dhc.github.framework.base.HAggregator;
import com.dhc.github.framework.base.HPersistent;
import com.dhc.github.framework.exception.HomException;
import com.dhc.github.framework.hbase.HPager;
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

    /**
     * 构造一个用于插入记录的Criteria
     * @param poType PO所属类型
     * @param <T>
     * @return
     */
    public static <T> PutCriteriaBuilder<T> putCriteria(Class<T> poType) {
        return new PutCriteriaBuilder<>();
    }

    /**
     * 构造一个用于查询的Criteria
     * @param poType PO所属类型
     * @param <T>
     * @return
     */
    public static <T> QueryCriteriaBuilder<T> findCriteria(Class<T> poType) {
        return new QueryCriteriaBuilder<>();
    }

    /**
     * 构造一个用于删除记录的Criteria
     * @param poType PO所属类型
     * @param <T>
     * @return
     */
    public static <T> DeleteCriteriaBuilder<T> deleteCriteria(Class<T> poType) {
        return new DeleteCriteriaBuilder<>();
    }

    /**
     * 构造一个用于聚合的Criteria
     * @param poType PO所属类型
     * @param <T>
     * @return
     */
    public static <T> AggregationCriteriaBuilder<T> aggregateCriteria(Class<T> poType) {
        return new AggregationCriteriaBuilder<>();
    }

    public void execute(HPersistent hPersistent) throws HomException {

        if (operation instanceof PutOperation) {
            doPutOperation(hPersistent);
        } else if (operation instanceof DeleteOperation) {
            doDeleteOperation(hPersistent);
        } else {
            throw new HomException("operation " + operation.getClass().getName() +
                    "does not match return type void");
        }
    }

    private void doDeleteOperation(HPersistent hPersistent) throws HomException {
        DeleteOperation<?> deleteOperation = (DeleteOperation<?>) operation;
        if (deleteOperation.getRowKey() != null) {
            hPersistent.deleteOne(deleteOperation.getRowKey(), deleteOperation.getPoType());
        } else if (deleteOperation.getRowKeys() != null) {
            hPersistent.deleteList(deleteOperation.getRowKeys(), deleteOperation.getPoType());
        } else if (deleteOperation.getPo() != null) {
            hPersistent.deleteOne(deleteOperation.getPo());
        } else if (deleteOperation.getPoList() != null) {
            hPersistent.deleteList(deleteOperation.getPoList());
        } else {
            throw new HomException("deleting operation is not correctly built");
        }
    }

    private void doPutOperation(HPersistent hPersistent) throws HomException {
        PutOperation<?> putOperation = (PutOperation<?>) operation;
        if (putOperation.getPo() != null) {
            hPersistent.putOne(putOperation.getPo());
        } else if (putOperation.getPoList() != null) {
            hPersistent.putOne(putOperation.getPoList());
        } else {
            throw new HomException("queryOne operation is not correctly built");
        }
    }

    public long count(HAggregator hAggregator) throws HomException {
        if (operation instanceof AggregationOperation) {
            AggregationOperation<?> op = (AggregationOperation<?>) operation;
            if (op.getStartRow() != null && op.getEndRow() != null) {
                return hAggregator.count(op.getStartRow(), op.getEndRow(), op.getPoType(), op.getFilters());
            }
            throw new HomException("aggregation operation is not correctly built");
        }
        throw new HomException("operation does not match");
    }

    public double sum(HAggregator hAggregator) throws HomException {
        if (operation instanceof AggregationOperation) {
            AggregationOperation<?> op = (AggregationOperation<?>) operation;
            if (op.getStartRow() != null && op.getEndRow() != null
                    && Strings.isNotEmpty(op.getCloumnName())) {
                return hAggregator.sum(op.getStartRow(), op.getEndRow(), op.getPoType(),
                        op.getCloumnName(), op.getFilters());
            }
            throw new HomException("aggregation operation is not correctly built");
        }
        throw new HomException("operation does not match");
    }

    public <T> T query(HPersistent hPersistent) throws HomException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getRowKey() == null) {
                throw new HomException("rowkey must be set for this operation");
            }
            return hPersistent.queryOne(op.getRowKey(), op.getPoType());
        }
        throw new HomException("operation does not match");
    }

    public <T> List<T> queryList(HPersistent hPersistent) throws HomException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getRowKeys() != null) {
                return hPersistent.queryList(op.getRowKeys(), op.getPoType());
            } else if (op.getFromRow() != null && op.getEndRow() != null) {
                return hPersistent.queryList(op.getFromRow(), op.getEndRow(), op.getPoType());
            }

            throw new HomException("queryOne operation is not correctly built");
        }
        throw new HomException("operation does not match");
    }

    public <T> HPager<T> queryByPage(HPersistent hPersistent) throws HomException {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getPager() != null) {
                return hPersistent.queryByPage(op.getPager(), op.getFilters());
            }

            throw new HomException("pager must be set for this operation");
        }
        throw new HomException("operation does not match");
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

        public HCriteria build(){
            return new HCriteria(aggregationOperation);
        }
    }
}
