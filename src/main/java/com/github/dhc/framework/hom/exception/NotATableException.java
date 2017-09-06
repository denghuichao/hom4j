/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : NotATableException.java
 */
package com.github.dhc.framework.hom.exception;

/**
 * @author : huichaodeng
 * @version 1.0 2017-08-28
 * @since hom 1.0
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
