package com.dhc.github.framework.hbase;

import com.dhc.github.framework.annotation.Column;
import com.dhc.github.framework.base.HAggregator;
import com.dhc.github.framework.base.HDataSourceAware;
import com.dhc.github.framework.base.HPersistent;
import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.exception.HomException;
import com.dhc.github.framework.exception.NotAColumnException;
import com.dhc.github.framework.exception.NotATableException;
import com.dhc.github.framework.parser.TypeParsers;
import com.dhc.github.framework.utils.HBaseUtil;
import com.dhc.github.framework.utils.PoExtractor;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.DoubleColumnInterpreter;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HomClient implements HDataSourceAware, HAggregator, HPersistent {

    private static final int PAGE_SIZE_NO_LIMIT = -1;
    /**
     * default cache in scan
     */
    private int scanCaching = 100;

    public HDataSource hDataSource;

    public HomClient() {
    }

    public HomClient(int scanCaching) {
        this.scanCaching = scanCaching;
    }

    @Override
    public HDataSource getHDataSource() {
        return this.hDataSource;
    }

    @Override
    public void setHDataSource(HDataSource dataSource) {
        this.hDataSource = dataSource;
    }


    @Override
    public long count(byte[] startRow, byte[] endRow, Class<?> po, Filter... filters) {
        if (startRow != null && endRow != null && po != null) {
            Scan scan = buildScan(startRow, endRow, PAGE_SIZE_NO_LIMIT, filters);
            LongColumnInterpreter columnInterpreter = new LongColumnInterpreter();
            AggregationClient aggregationClient = buildAggregationClient();
            try {
                return aggregationClient.rowCount(TableName.valueOf(HBaseUtil.getHTableName(po)),
                        columnInterpreter, scan);
            } catch (Throwable e) {
                throw new HomException(e);
            }
        }
        throw new IllegalArgumentException("startRow, endRow and poType must not be null");
    }

    private Scan buildScan(byte[] startRow, byte[] endRow, int pageSize, Filter... filters) {
        Scan scan = new Scan();
        scan.setStartRow(startRow);
        scan.setStopRow(endRow);

        List<Filter> filterList = Lists.newArrayList();
        if (pageSize != PAGE_SIZE_NO_LIMIT)
            filterList.add(new PageFilter(pageSize));


        filterList.addAll(Arrays.asList(filters));

        if (filterList.size() > 0) {
            FilterList f = new FilterList(filterList.toArray(new Filter[filterList.size()]));
            scan.setFilter(f);
        }

        scan.setCaching(scanCaching);
        return scan;
    }

    private AggregationClient buildAggregationClient() {
        AggregationClient aggregationClient = new AggregationClient(getHDataSource().getConfig());
        return aggregationClient;
    }


    @Override
    public double sum(byte[] startRow, byte[] endRow, Class<?> po, String propertyName, Filter... filters) {
        if (startRow != null && endRow != null && po != null && propertyName != null) {
            Scan scan = buildScan(startRow, endRow, PAGE_SIZE_NO_LIMIT, filters);
            AggregationClient aggregationClient = buildAggregationClient();
            DoubleColumnInterpreter doubleColumnInterpreter = new DoubleColumnInterpreter();

            try {
                indicateColumnToAggregate(po, propertyName, scan);
                return aggregationClient.sum(TableName.valueOf(HBaseUtil.getHTableName(po)), doubleColumnInterpreter, scan);
            } catch (Throwable e) {
                throw new HomException(e);
            }
        }
        throw new IllegalArgumentException("startRow, endRow  poType, and propertyName must not be null");
    }

    private void indicateColumnToAggregate(Class<?> po, String propertyName, Scan scan)
            throws NoSuchFieldException, SecurityException, HomException {
        Field sumField = po.getDeclaredField(propertyName);
        if (!sumField.isAnnotationPresent(Column.class)) {
            throw new NotAColumnException("field :[" + propertyName + "] is not annotated as an hcolumn");
        }
        Column column = sumField.getAnnotation(Column.class);

        String cn = Strings.isNullOrEmpty(column.name()) ? sumField.getName() : column.name();

        scan.addColumn(TypeParsers.toBytes(column.family()), TypeParsers.toBytes(cn));
    }

    @Override
    public <T> void putOne(T po) {
        if (po != null) {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(po));
            Put put = buildPut(Arrays.asList(po)).get(0);
            try {
                table.put(put);
            } catch (Throwable e) {
                throw new HomException(e);
            }

        }
        throw new IllegalArgumentException("po object must not be null");
    }

    private <T> List<Put> buildPut(List<T> pos) {
        if (CollectionUtils.isEmpty(pos))
            return Lists.newArrayList();

        List<Put> puts = Lists.newArrayList();
        List<HColumnDefinition> columnInfoList = HBaseUtil.getHColumnDefinitions(pos.get(0));
        HColumnDefinition rowKeyhcd = HBaseUtil.getRowKey(columnInfoList);
        for (T t : pos) {
            try {
                byte[] rowKey = TypeParsers.toBytes(rowKeyhcd.getField().get(t));
                Put put = new Put(rowKey);
                for (HColumnDefinition colDef : columnInfoList) {
                    if (colDef.getField().get(t) != null) {
                        byte[] fieldValue = TypeParsers.toBytes(colDef.getField().get(t));

                        put.add(TypeParsers.toBytes(colDef.getColumnFamily()),
                                TypeParsers.toBytes(colDef.getColummName()), fieldValue);
                    }
                }
                puts.add(put);
            } catch (Throwable e) {
                throw new HomException(e);
            }
        }
        return puts;
    }

    @Override
    public <T> void putList(List<T> poList) {
        if (CollectionUtils.isNotEmpty(poList)) {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(poList.get(0)));
            List<Put> puts = buildPut(poList);
            try {
                table.put(puts);
            } catch (IOException e) {
                throw new HomException(e);
            }
        }
    }

    @Override
    public <T> T queryOne(byte[] rowKey, Class<T> poType) {
        if (rowKey != null && poType != null) {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(poType));
            Get get = new Get(rowKey);
            try {
                return PoExtractor.extract(table.get(get), poType);
            } catch (IOException e) {
                throw new HomException(e);
            }
        }
        throw new IllegalArgumentException("po rowkey and poType must not be null");
    }


    @Override
    public <T> List<T> queryList(List<byte[]> rowKeys, Class<T> poType) {
        if (rowKeys != null && poType != null) {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(poType));
            List<Get> gets = Lists.newArrayList();
            rowKeys.forEach(rowKey -> gets.add(new Get(rowKey)));

            try {
                return PoExtractor.extract(table.get(gets), poType);
            } catch (IOException e) {
                throw new HomException(e);
            }

        }
        throw new IllegalArgumentException("po rowkeys and poType must not be null");
    }


    @Override
    public <T> List<T> queryList(byte[] fromRowKey, byte[] endRowKey, Class<T> poType) {
        if (fromRowKey != null && endRowKey != null && poType != null) {
            try {
                Table table = getHDataSource().getTable(HBaseUtil.getHTableName(poType));
                Scan scan = buildScan(fromRowKey, endRowKey, PAGE_SIZE_NO_LIMIT);
                ResultScanner scanner = table.getScanner(scan);
                List<T> res = Lists.newArrayList();
                scanner.forEach(e -> res.add(PoExtractor.extract(e, poType)));
                return res;
            } catch (IOException e) {
                throw new HomException(e);
            }
        }
        throw new IllegalArgumentException("po rowkeys and poType must not be null");
    }


    @Override
    public <T> HPager<T> queryByPage(HPager<T> hPager, Filter... filters) {
        Scan scan = buildScan(hPager.getStartRow(), hPager.getStopRow(), hPager.getPageSize(), filters);
        List<T> resultList = Lists.newArrayList();

        try {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(hPager.getGenericType()));
            ResultScanner scanner = table.getScanner(scan);
            scanner.forEach(e -> resultList.add((T) PoExtractor.extract(e, hPager.getGenericType())));
            hPager.setRecordList(resultList);
            if (resultList != null && resultList.size() > 0) {
                // the rowkey of the last po will be the start rowkey of the next page
                T t = resultList.get(resultList.size() - 1);
                // make startRow exclusive add a trailing 0 byte
                hPager.setStartRow(Bytes.add(HBaseUtil.getRowKeyBytes(t), new byte[]{0}));
            }
            hPager.setPageNumber(hPager.getPageNumber() + 1);
            return hPager;
        } catch (IOException e) {
            throw new HomException(e);
        }
    }

    @Override
    public void deleteOne(byte[] rowKey, Class<?> poType) {
        if (rowKey != null && poType != null) {
            deleteList(Arrays.asList(rowKey), poType);
        }
        throw new IllegalArgumentException("po rowkey and poType must not be null");
    }

    @Override
    public void deleteList(List<byte[]> rowKeys, Class<?> poType) {
        if (rowKeys != null && poType != null) {
            Table table = getHDataSource().getTable(HBaseUtil.getHTableName(poType));
            List<Delete> deletes = Lists.newArrayList();
            rowKeys.forEach(rowKey -> deletes.add(new Delete(rowKey)));

            if (deletes.size() > 0) {
                try {
                    table.delete(deletes);
                } catch (IOException e) {
                    throw new HomException(e);
                }
            }
        }
        throw new IllegalArgumentException("po rowkeys and poType must not be null");
    }

    @Override
    public <T> void deleteOne(T po) {
        if (po != null)
            deleteList(Arrays.asList(po));

        throw new IllegalArgumentException("po object must not be null");
    }

    @Override
    public <T> void deleteList(List<T> poList) {
        if (poList != null) {
            if (poList.size() > 0) {
                Class<?> type = poList.get(0).getClass();
                List<byte[]> rowKeys = Lists.newArrayList();
                poList.forEach(t -> rowKeys.add(HBaseUtil.getRowKeyBytes(t)));
                deleteList(rowKeys, type);
            }
        }
        throw new IllegalArgumentException("po list must not be null");
    }
}
