/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-25
 * Project        : horm
 * File Name      : Book.java
 */
package com.dhc.github.framework.pos;

import com.dhc.github.framework.annotation.Column;
import com.dhc.github.framework.annotation.RowKey;
import com.dhc.github.framework.annotation.Table;

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

    @RowKey
    private int bookId;

    @Column(family = "info", name = "bookName")
    private String bookName;

    @Column(family = "info", name = "author")
    private String author;

    @Column(family = "info", name = "desc")
    private String desc;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

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
