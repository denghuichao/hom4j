package com.ctrip.tourtailor.framework.hbase;

import com.ctrip.tourtailor.framework.base.HDataSourceAware;
import com.ctrip.tourtailor.framework.base.HSchema;
import com.ctrip.tourtailor.framework.exception.HomException;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

/**
 * Created by hcdeng on 17-8-28.
 */
public class HomAdmin implements HDataSourceAware, HSchema{

    private HDataSource hDataSource;

    @Override
    public void createTable(String tableName, String... columnFamilies) throws HomException {
        try(Admin admin = getHDataSource().getHBaseAdmin()){
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
            for (String fc : columnFamilies) {
                HColumnDescriptor hcd = new HColumnDescriptor(fc);
                htd.addFamily(hcd);
            }
            admin.createTable(htd);
            admin.close();
        }catch (Throwable e){
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
    public void deleteTable(String tableName) throws HomException {
        try(Admin admin = getHDataSource().getHBaseAdmin()){
            TableName tName = TableName.valueOf(tableName);
            admin.disableTable(tName);
            admin.deleteTable(tName);
            admin.close();
        }catch (Throwable e){
            throw new HomException(e);
        }
    }
}
