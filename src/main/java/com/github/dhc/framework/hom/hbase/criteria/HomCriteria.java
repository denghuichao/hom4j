package com.github.dhc.framework.hom.hbase.criteria;

import com.github.dhc.framework.hom.base.HAggregator;
import com.github.dhc.framework.hom.base.HPersistent;
import com.github.dhc.framework.hom.exception.HomException;
import com.github.dhc.framework.hom.hbase.HPager;
import com.github.dhc.framework.hom.utils.HBaseUtil;
import org.apache.directory.api.util.Strings;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HomCriteria {

    private Operation<?> operation;

    public HomCriteria(Operation<?> operation) {
        this.operation = operation;
    }

    /**
     * Construct a Criteria for putting records
     * @param poType the the of po to be saved
     * @param <T>
     * @return
     */
    public static <T> PutCriteriaBuilder<T> putCriteria(Class<T> poType) {
        return new PutCriteriaBuilder<>(poType);
    }

    /**
     * Construct a Criteria for querying records
     * @param poType the the of po to be queried
     * @param <T>
     * @return
     */
    public static <T> QueryCriteriaBuilder<T> findCriteria(Class<T> poType) {
        return new QueryCriteriaBuilder<>(poType);
    }

    /**
     * Construct a Criteria for deleting records
     * @param poType the the of po to be deleted
     * @param <T>
     * @return
     */
    public static <T> DeleteCriteriaBuilder<T> deleteCriteria(Class<T> poType) {
        return new DeleteCriteriaBuilder<>(poType);
    }

    /**
     * Construct a Criteria for aggregation operations
     * @param poType the the of po to be queried
     * @param <T>
     * @return
     */
    public static <T> AggregationCriteriaBuilder<T> aggregateCriteria(Class<T> poType) {
        return new AggregationCriteriaBuilder<>(poType);
    }

    public Object execute(HPersistent hPersistent) {

        if (operation instanceof PutOperation) {
           return doPutOperation(hPersistent);
        } else if (operation instanceof DeleteOperation) {
          return doDeleteOperation(hPersistent);
        } else {
            throw new HomException("operation " + operation.getClass().getName() +
                    "does not match return type void");
        }
    }

    private Object doDeleteOperation(HPersistent hPersistent) {
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
        return null;
    }

    private Object doPutOperation(HPersistent hPersistent) {
        PutOperation<?> putOperation = (PutOperation<?>) operation;
        if (putOperation.getPo() != null) {
           return hPersistent.putOne(putOperation.getPo());
        } else if (putOperation.getPoList() != null) {
           return hPersistent.putList(putOperation.getPoList());
        } else {
            throw new HomException("queryOne operation is not correctly built");
        }
    }

    public long count(HAggregator hAggregator) {
        if (operation instanceof AggregationOperation) {
            AggregationOperation<?> op = (AggregationOperation<?>) operation;
            if (op.getStartRow() != null && op.getEndRow() != null) {
                return hAggregator.count(op.getStartRow(), op.getEndRow(), op.getPoType(), op.getFilters());
            }
            throw new HomException("aggregation operation is not correctly built");
        }
        throw new HomException("operation does not match");
    }

    public double sum(HAggregator hAggregator) {
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

    public <T> T query(HPersistent hPersistent) {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getRowKey() == null) {
                throw new HomException("rowkey must be set for this operation");
            }
            return hPersistent.queryOne(op.getRowKey(), op.getPoType());
        }
        throw new HomException("operation does not match");
    }

    public <T> List<T> queryList(HPersistent hPersistent) {
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

    public <T> HPager<T> queryByPage(HPersistent hPersistent)  {
        if (operation instanceof QueryOperation) {
            QueryOperation<T> op = (QueryOperation<T>) operation;
            if (op.getPager() != null) {
                return hPersistent.queryByPage(op.getPager(), op.getFilters());
            }

            throw new HomException("pager must be set for this operation");
        }
        throw new HomException("operation does not match");
    }

    /*CriteriaBuilder definition*/
    public static class PutCriteriaBuilder<T> {

        private PutOperation<T> putOperation;

        public PutCriteriaBuilder(Class<T> poType) {
            putOperation = new PutOperation<>();
            putOperation.setPoType(poType);
        }

        public PutCriteriaBuilder putObject(T t) {
            putOperation.setPo(t);
            return this;
        }

        public PutCriteriaBuilder putList(List<T> poList) {
            putOperation.setPoList(poList);
            return this;
        }

        public HomCriteria build() {
            return new HomCriteria(putOperation);
        }
    }

    public static class QueryCriteriaBuilder<T> {

        private QueryOperation<T> qop;

        public QueryCriteriaBuilder(Class<T> poType) {
            qop = new QueryOperation<>();
            qop.setPoType(poType);
        }

        public QueryCriteriaBuilder byRowKey(byte[] rowKey) {
            qop.setRowKey(rowKey);
            return this;
        }

        /**
         * rowKey should not be byte array here
         * @param rowKey
         * @return
         */
        public QueryCriteriaBuilder byRowKey(Object rowKey) {
            qop.setRowKey(HBaseUtil.objectToRowkey(rowKey, qop.getPoType()));
            return this;
        }

        public QueryCriteriaBuilder byRowKeys(List<byte[]> rowKeys) {
            qop.setRowKeys(rowKeys);
            return this;
        }

        /**
         * rowKey should not be byte array here
         * @param rowKeys
         * @return
         */
        public QueryCriteriaBuilder byRowKeys_1(List<?> rowKeys) {
            qop.setRowKeys(HBaseUtil.objectsToRowkeys(rowKeys, qop.getPoType()));
            return this;
        }

        public QueryCriteriaBuilder fromRow(byte[] fromRow) {
            qop.setFromRow(fromRow);
            return this;
        }

        public QueryCriteriaBuilder fromRow(Object fromRow) {
            qop.setFromRow(HBaseUtil.objectToRowkey(fromRow, qop.getPoType()));
            return this;
        }

        public QueryCriteriaBuilder toRow(byte[] endRow) {
            qop.setEndRow(endRow);
            return this;
        }

        public QueryCriteriaBuilder toRow(Object endRow) {
            qop.setEndRow(HBaseUtil.objectToRowkey(endRow, qop.getPoType()));
            return this;
        }

        public QueryCriteriaBuilder withFilters(Filter... filters) {
            qop.setFilters(filters);
            return this;
        }

        public QueryCriteriaBuilder byPage(HPager<T> pager) {
            qop.setPager(pager);
            return this;
        }

        public HomCriteria build() {
            return new HomCriteria(qop);
        }
    }

    public static class DeleteCriteriaBuilder<T> {
        private DeleteOperation<T> dop;

        public DeleteCriteriaBuilder(Class<T> poType) {
            this.dop = new DeleteOperation<>();
            dop.setPoType(poType);
        }

        public DeleteCriteriaBuilder byRowKey(byte[] rowKey) {
            dop.setRowKey(rowKey);
            return this;
        }

        public DeleteCriteriaBuilder byRowKey(Object rowKey) {
            dop.setRowKey(HBaseUtil.objectToRowkey(rowKey, dop.getPoType()));
            return this;
        }

        public DeleteCriteriaBuilder byRowKeys(List<byte[]> rowKeys) {
            dop.setRowKeys(rowKeys);
            return this;
        }

        public DeleteCriteriaBuilder byRowKeys_1(List<?> rowKeys) {
            dop.setRowKeys(HBaseUtil.objectsToRowkeys(rowKeys, dop.getPoType()));
            return this;
        }

        public DeleteCriteriaBuilder byPo(T po) {
            dop.setPo(po);
            return this;
        }

        public DeleteCriteriaBuilder byPos(List<T> pos) {
            dop.setPoList(pos);
            return this;
        }

        public HomCriteria build() {
            return new HomCriteria(dop);
        }
    }

    public static class AggregationCriteriaBuilder<T> {
        private AggregationOperation<T> aop;

        public AggregationCriteriaBuilder(Class<T> poType) {
            aop = new AggregationOperation<>();
            aop.setPoType(poType);
        }

        public AggregationCriteriaBuilder fromRow(byte[] startRow) {
            aop.setStartRow(startRow);
            return this;
        }

        public AggregationCriteriaBuilder fromRow(Object startRow) {
            aop.setStartRow(HBaseUtil.objectToRowkey(startRow, aop.getPoType()));
            return this;
        }

        public AggregationCriteriaBuilder toRow(byte[] endRow) {
            aop.setEndRow(endRow);
            return this;
        }

        public AggregationCriteriaBuilder toRow(Object endRow) {
            aop.setEndRow(HBaseUtil.objectToRowkey(endRow, aop.getPoType()));
            return this;
        }

        public AggregationCriteriaBuilder byFilters(Filter... filters) {
            aop.setFilters(filters);
            return this;
        }

        public AggregationCriteriaBuilder byCloumn(String columnName) {
            aop.setCloumnName(columnName);
            return this;
        }

        public HomCriteria build(){
            return new HomCriteria(aop);
        }
    }
}
