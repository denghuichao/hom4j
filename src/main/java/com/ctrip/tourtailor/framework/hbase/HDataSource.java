package com.ctrip.tourtailor.framework.hbase;

import com.ctrip.tourtailor.framework.exception.HOrmException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HDataSource {
    private Configuration config;
    private Connection connection;

    public HDataSource(Configuration config) throws HOrmException{
        this.config = config;
        try {
            connection = ConnectionFactory.createConnection(config);
        }catch (IOException e){
            throw new HOrmException(e);
        }
    }


    public Table getTable(String tableName)throws HOrmException{
        try {
            return getConnection().getTable(TableName.valueOf(tableName));
        }catch (IOException e){
            throw new HOrmException(e);
        }
    }

    public Admin getHBaseAdmin()throws HOrmException{
        try {
            return getConnection().getAdmin();
        }catch (IOException e){
            throw new HOrmException(e);
        }
    }

    public void closeTable(Table table)throws HOrmException{
        if(table != null){
            try {
                table.close();
            }catch (IOException e){
                throw new HOrmException(e);
            }
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public Connection getConnection() {
        return connection;
    }


}
