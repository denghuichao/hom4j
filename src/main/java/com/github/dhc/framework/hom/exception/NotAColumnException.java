/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : NotAColumnException.java
 */
package com.github.dhc.framework.hom.exception;

/**
 * @author : huichaodeng
 * @version 1.0 2017-08-28
 * @since hom 1.0
 */
public class NotAColumnException extends HomException{
    public NotAColumnException(){
        super();
    }

    public NotAColumnException(String message) {
        super(message);
    }

    public NotAColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAColumnException(Throwable cause) {
        super(cause);
    }
}
