/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-25
 * Project        : horm
 * File Name      : CriteriaTest.java
 */
package com.github.dhc.framework.hom;

import com.github.dhc.framework.hom.conf.HDataSourceConfig;
import com.github.dhc.framework.hom.exception.HomException;
import com.github.dhc.framework.hom.hbase.HDataSource;
import com.github.dhc.framework.hom.hbase.HPager;
import com.github.dhc.framework.hom.hbase.HomClient;
import com.github.dhc.framework.hom.hbase.criteria.HomCriteria;
import com.github.dhc.framework.hom.utils.parser.TypeParsers;
import com.github.dhc.framework.hom.pos.SchemeSnapshot;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-25
 * @since horm 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CriteriaTest {

    private static HomClient homClient;

    @BeforeClass
    public static void setUp() throws Exception {
        homClient = new HomClient();
        homClient.setHDataSource(new HDataSource(HDataSourceConfig.getConfiguration()));
    }

    @Test
    public void test_1_PutCriteria_One() {
        SchemeSnapshot schemeSnapshot = new SchemeSnapshot();
        schemeSnapshot.setSnapShortID(1l);
        schemeSnapshot.setSnapShortStatus("Active");
        schemeSnapshot.setSnapShortTime(Calendar.getInstance());
        schemeSnapshot.setOrderID(123l);
        schemeSnapshot.setPlatformUserId("123445");
        schemeSnapshot.setPlatformProviderId(123456l);
        schemeSnapshot.setDataChangeCreateTime(Calendar.getInstance());
        schemeSnapshot.setDataChangeLastTime(Calendar.getInstance());
        schemeSnapshot.setCreateUser("zhangsan");
        schemeSnapshot.setModifyUser("lisi");
        schemeSnapshot.setSchemeInfo("this is the scheme info");
        Object key = HomCriteria.putCriteria(SchemeSnapshot.class)
                .putObject(schemeSnapshot)
                .build()
                .execute(homClient);

        Assert.assertNotNull(key);
    }

    @Test
    public void test_2_PutCriteria_List(){
        SchemeSnapshot schemeSnapshot2 = new SchemeSnapshot();
        schemeSnapshot2.setSnapShortID(2l);
        schemeSnapshot2.setSnapShortStatus("Active");
        schemeSnapshot2.setSnapShortTime(Calendar.getInstance());
        schemeSnapshot2.setOrderID(123l);
        schemeSnapshot2.setPlatformUserId("123445");
        schemeSnapshot2.setPlatformProviderId(123456l);
        schemeSnapshot2.setDataChangeCreateTime(Calendar.getInstance());
        schemeSnapshot2.setDataChangeLastTime(Calendar.getInstance());
        schemeSnapshot2.setCreateUser("zhangsan");
        schemeSnapshot2.setModifyUser("lisi");
        schemeSnapshot2.setSchemeInfo("this is the scheme info");

        SchemeSnapshot schemeSnapshot3 = new SchemeSnapshot();
        schemeSnapshot3.setSnapShortID(3l);
        schemeSnapshot3.setSnapShortStatus("Active");
        schemeSnapshot3.setSnapShortTime(Calendar.getInstance());
        schemeSnapshot3.setOrderID(123l);
        schemeSnapshot3.setPlatformUserId("123445");
        schemeSnapshot3.setPlatformProviderId(123456l);
        schemeSnapshot3.setDataChangeCreateTime(Calendar.getInstance());
        schemeSnapshot3.setDataChangeLastTime(Calendar.getInstance());
        schemeSnapshot3.setCreateUser("zhangsan");
        schemeSnapshot3.setModifyUser("lisi");
        schemeSnapshot3.setSchemeInfo("this is the scheme info");

        Object keys = HomCriteria.putCriteria(SchemeSnapshot.class)
                .putList(Arrays.asList(schemeSnapshot2, schemeSnapshot3))
                .build()
                .execute(homClient);

        Assert.assertNotNull(keys);
    }

    //po 的rowkey为null，应该异常
    @Test
    public void test_3_PutCriteriaRowKeyIsNull() {
        SchemeSnapshot schemeSnapshot = new SchemeSnapshot();
        schemeSnapshot.setSnapShortStatus("Active");
        schemeSnapshot.setSnapShortTime(Calendar.getInstance());
        schemeSnapshot.setOrderID(123l);
        schemeSnapshot.setPlatformUserId("123445");
        schemeSnapshot.setPlatformProviderId(123456l);
        schemeSnapshot.setDataChangeCreateTime(Calendar.getInstance());
        schemeSnapshot.setDataChangeLastTime(Calendar.getInstance());
        schemeSnapshot.setCreateUser("zhangsan");
        schemeSnapshot.setModifyUser("lisi");
        schemeSnapshot.setSchemeInfo("this is the scheme info");
        Object key = HomCriteria.putCriteria(SchemeSnapshot.class)
                .putObject(schemeSnapshot)
                .build()
                .execute(homClient);

        Assert.assertNotNull(key);
    }


    @Test
    public void test_4_QueryCriteria_ONE(){
        SchemeSnapshot schemeSnapshot1 = HomCriteria.findCriteria(SchemeSnapshot.class)
                .byRowKey(TypeParsers.toBytes(1l))
                .build()
                .query(homClient);

        List<SchemeSnapshot> list = HomCriteria.findCriteria(SchemeSnapshot.class)
                .fromRow(TypeParsers.toBytes(1l))
                .toRow(TypeParsers.toBytes(3l))
                .build()
                .queryList(homClient);

        Assert.assertEquals(list.size(), 2);
    }


    @Test
    public void test_5_QueryCriteria_FromTo(){
        List<SchemeSnapshot> list = HomCriteria.findCriteria(SchemeSnapshot.class)
                .fromRow(TypeParsers.toBytes(1l))
                .toRow(TypeParsers.toBytes(3l))
                .build()
                .queryList(homClient);

        Assert.assertEquals(list.size(), 2);
    }

    @Test
    public void test_6_QueryCriteria_List(){
        List<SchemeSnapshot> list = HomCriteria.findCriteria(SchemeSnapshot.class)
                .byRowKeys(Arrays.asList(TypeParsers.toBytes(1l),TypeParsers.toBytes(2l)))
                .build()
                .queryList(homClient);

        Assert.assertEquals(list.size(), 2);
    }

    //不存在，应该返回null
    @Test
    public void test_7_QueryCriteria_NotExist(){
        SchemeSnapshot schemeSnapshot1 = HomCriteria.findCriteria(SchemeSnapshot.class)
                .byRowKey(TypeParsers.toBytes(5l))
                .build()
                .query(homClient);

        Assert.assertEquals(schemeSnapshot1, null);
    }


    @Test
    public void test_8_QueryCriteria_ByPage(){
        HPager<SchemeSnapshot> pager = new HPager<>(SchemeSnapshot.class);
        pager.setPageSize(2);
        pager.setStartRow(1l);
        pager.setStopRow(4l);
        pager = HomCriteria.findCriteria(SchemeSnapshot.class).byPage(pager).build().queryByPage(homClient);
        Assert.assertNotNull(pager.getRecordList());
        Assert.assertTrue(pager.getRecordList().size() > 0);
    }

    @Test
    public void test_9_QueryCriteria_ByPage_NoRecord(){
        HPager<SchemeSnapshot> pager = new HPager<>(SchemeSnapshot.class);
        pager.setPageSize(10);
        pager.setStartRow(Long.MAX_VALUE - 5);
        pager.setStopRow(Long.MAX_VALUE);
        pager = HomCriteria.findCriteria(SchemeSnapshot.class).byPage(pager).build().queryByPage(homClient);
        Assert.assertNotNull(pager.getRecordList());
        Assert.assertTrue(pager.getRecordList().size() == 0);
    }

    @Test
    public void test_90_DeleteCriteria_One(){
        HomCriteria.deleteCriteria(SchemeSnapshot.class)
                .byRowKey(TypeParsers.toBytes(1l))
                .build().execute(homClient);
    }


    @Test
    public void test_91_DeleteCriteria_List(){
        HomCriteria.deleteCriteria(SchemeSnapshot.class)
                .byRowKeys(Arrays.asList(TypeParsers.toBytes(1l), TypeParsers.toBytes(2l)))
                .build().execute(homClient);
    }

    //删除不存在的rowkey，不应该报错
    @Test
    public void test_92_DeleteCriteria_NotExist(){
        HomCriteria.deleteCriteria(SchemeSnapshot.class)
                .byRowKey(TypeParsers.toBytes(1999l))
                .build().execute(homClient);
    }

    @Test(expected = HomException.class)
    public void test_93_AggregationCriteria(){
        long count = HomCriteria.aggregateCriteria(SchemeSnapshot.class)
                .fromRow(TypeParsers.toBytes(1))
                .toRow(TypeParsers.toBytes(4))
                .build()
                .count(homClient);
        Assert.assertEquals(count, 3);

        double sum = HomCriteria.aggregateCriteria(SchemeSnapshot.class)
                .fromRow(TypeParsers.toBytes(1))
                .toRow(TypeParsers.toBytes(4))
                .byCloumn("orderID")
                .build()
                .sum(homClient);

        Assert.assertEquals(sum, 123l * 3);
    }
}
