/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-25
 * Project        : horm
 * File Name      : Book.java
 */
package com.ctrip.tourtailor.framework;

import com.ctrip.tourtailor.framework.annotation.Column;
import com.ctrip.tourtailor.framework.annotation.Table;

import java.io.Serializable;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-25
 * @since horm 1.0
 */
@Table("book")
public class Book implements Serializable{

    @Column(family = "info", name = "bookName")
    private String bookName;

    @Column(family = "info", name = "author")
    private String author;

    @Column(family = "info", name = "desc")
    private String desc;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
