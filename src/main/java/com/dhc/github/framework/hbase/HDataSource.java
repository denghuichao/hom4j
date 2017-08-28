package com.dhc.github.framework.hbase;

import com.dhc.github.framework.exception.HomException;
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

    public HDataSource(Configuration config) {
        this.config = config;
        try {
            connection = ConnectionFactory.createConnection(config);
        }catch (IOException e){
            throw new HomException(e);
        }
    }


    public Table getTable(String tableName) {
        try {
            return getConnection().getTable(TableName.valueOf(tableName));
        }catch (IOException e){
            throw new HomException(e);
        }
    }

    public Admin getHBaseAdmin() {
        try {
            return getConnection().getAdmin();
        }catch (IOException e){
            throw new HomException(e);
        }
    }

    public void closeTable(Table table) {
        if(table != null){
            try {
                table.close();
            }catch (IOException e){
                throw new HomException(e);
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
