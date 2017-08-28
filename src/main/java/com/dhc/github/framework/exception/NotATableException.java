/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : NotATableException.java
 */
package com.dhc.github.framework.exception;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-28
 * @since horm 1.0
 */
public class NotATableException extends HomException{

    public NotATableException(){
        super();
    }

    public NotATableException(String message) {
        super(message);
    }

    public NotATableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotATableException(Throwable cause) {
        super(cause);
    }
}
