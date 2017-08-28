package com.dhc.github.framework.hbase;

import com.dhc.github.framework.base.HDataSourceAware;
import com.dhc.github.framework.base.HSchema;
import com.dhc.github.framework.exception.HomException;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import java.io.IOException;

/**
 * Created by hcdeng on 17-8-28.
 */
public class HomAdmin implements HDataSourceAware, HSchema {

    private HDataSource hDataSource;

    @Override
    public void createTable(String tableName, String... columnFamilies) {
        try {
            Admin admin = getHDataSource().getHBaseAdmin();
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
            for (String fc : columnFamilies) {
                HColumnDescriptor hcd = new HColumnDescriptor(fc);
                htd.addFamily(hcd);
            }
            admin.createTable(htd);
            admin.close();
        } catch (IOException e) {
            throw new HomException(e);
        }
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
    public void deleteTable(String tableName) {
        try {
            Admin admin = getHDataSource().getHBaseAdmin();
            TableName tName = TableName.valueOf(tableName);
            admin.disableTable(tName);
            admin.deleteTable(tName);
            admin.close();
        } catch (IOException e) {
            throw new HomException(e);
        }
    }
}
