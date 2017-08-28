/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-25
 * Project        : horm
 * File Name      : CriteriaTest.java
 */
package com.dhc.github.framework;

import com.dhc.github.framework.conf.HDataSourceConfig;
import com.dhc.github.framework.exception.HomException;
import com.dhc.github.framework.hbase.HDataSource;
import com.dhc.github.framework.hbase.HomClient;
import com.dhc.github.framework.hbase.criteria.HCriteria;
import com.dhc.github.framework.parser.TypeParsers;
import com.dhc.github.framework.pos.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-25
 * @since horm 1.0
 */
public class CriteriaTest {

    private HomClient homClient;

    @Before
    public void setUp() throws Exception {
        homClient = new HomClient();
        homClient.setHDataSource(new HDataSource(HDataSourceConfig.getConfiguration()));
    }

    @Test
    public void testPutCriteria()throws HomException {
        Book book = new Book();
        book.setBookId(1234);
        book.setBookName("Thinking in java");
        book.setAuthor("hcdeng");
        book.setDesc("good book about java");
        HCriteria.putCriteria(Book.class).putObject(book).build().execute(homClient);
        HCriteria.putCriteria(Book.class).putList(Arrays.asList(book)).build().execute(homClient);
    }

    @Test
    public void testQueryCriteria()throws HomException{
        Book book = HCriteria.findCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().query(homClient);
        Assert.assertEquals(book.getBookId(), 1234);
        Assert.assertEquals(book.getAuthor(),"hcdeng");
        Assert.assertEquals(book.getDesc(),"good book about java");
        Assert.assertEquals(book.getBookName(),"Thinking in java");
    }

    @Test
    void testAggregationCriteria() throws HomException{
       long count = HCriteria.aggregateCriteria(Book.class).fromRow(TypeParsers.toBytes(1234)).toRow(TypeParsers.toBytes(1245))
                .build().count(homClient);
       Assert.assertEquals(count, 1);
    }

    @Test
    void testDeleteCriteria() throws HomException{
       HCriteria.deleteCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().execute(homClient);
    }
}
