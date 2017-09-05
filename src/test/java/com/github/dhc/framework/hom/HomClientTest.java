package com.github.dhc.framework.hom;

import com.github.dhc.framework.hom.conf.HDataSourceConfig;
import com.github.dhc.framework.hom.hbase.HDataSource;
import com.github.dhc.framework.hom.hbase.HomClient;
import com.github.dhc.framework.hom.pos.SchemeSnapshot;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hcdeng on 2017/8/31.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomClientTest {

    private static HomClient homClient;

    @BeforeClass
    public static void setUp() throws Exception {
        homClient = new HomClient();
        homClient.setHDataSource(new HDataSource(HDataSourceConfig.getConfiguration()));
    }

    @Test
    public void test_queryOneByCondition(){
        Map<String, Object> kvs = new HashMap<>();
        kvs.put("cf.orderID", 123l);
        SchemeSnapshot po = homClient.queryOne(kvs, SchemeSnapshot.class);
        Assert.assertNotNull(po);
    }

    @Test
    public void test_queryListByCondition(){
        Map<String, Object> kvs = new HashMap<>();
        kvs.put("cf.orderID", 123l);
        List<SchemeSnapshot> pos = homClient.queryList(kvs, SchemeSnapshot.class);
        Assert.assertNotNull(pos);
    }
}
