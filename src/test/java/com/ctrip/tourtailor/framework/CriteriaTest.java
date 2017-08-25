/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-25
 * Project        : horm
 * File Name      : CriteriaTest.java
 */
package com.ctrip.tourtailor.framework;

import com.ctrip.tourtailor.framework.exception.HOrmException;
import com.ctrip.tourtailor.framework.hbase.criteria.HCriteria;
import org.junit.Test;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-25
 * @since horm 1.0
 */
public class CriteriaTest {

    @Test
    public void testPutCriteria()throws HOrmException{
        Book book = new Book();
        book.setBookName("Thinking in java");
        book.setAuthor("hcdeng");
        book.setDesc("good book about java");
        HCriteria.put(Book.class).putObject(book).build().execute(null);
    }
}
