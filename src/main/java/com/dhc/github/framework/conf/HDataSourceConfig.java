package com.dhc.github.framework.conf;

import com.dhc.github.framework.exception.HomException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by hcdeng on 17-8-25.
 */
public class HDataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(HDataSourceConfig.class);

    private static volatile Configuration config = null;

    private static void loadConfig() throws IOException {

        InputStreamReader inputStream = new InputStreamReader(
                getClassLoader().getResourceAsStream("hom4j.properties"), "UTF-8");

        Properties properties = new Properties();
        properties.load(inputStream);
        config = HBaseConfiguration.create();
        for (Object key : properties.keySet()) {
            config.set(key.toString(), properties.getProperty(key.toString()));
        }

    }

    public static Configuration getConfiguration() {
        if (config == null) {
            synchronized (HDataSourceConfig.class) {
                if (config == null) {
                    try {
                        loadConfig();
                    } catch (Exception e) {
                        logger.error("get hbase configuration failed", e);
                        throw new HomException("et hbase configuration failed", e);
                    }
                }
            }
        }
        return config;
    }


    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
