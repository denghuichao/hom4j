/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : RowKeyNotDefineException.java
 */
package com.github.dhc.framework.hom.exception;

/**
 * @author : huichaodeng
 * @version 1.0 2017-08-28
 * @since hom 1.0
 */
public class RowKeyNotDefineException extends HomException{

    public RowKeyNotDefineException(){
        super();
    }

    public RowKeyNotDefineException(String message) {
        super(message);
    }

    public RowKeyNotDefineException(String message, Throwable cause) {
        super(message, cause);
    }

    public RowKeyNotDefineException(Throwable cause) {
        super(cause);
    }
}
