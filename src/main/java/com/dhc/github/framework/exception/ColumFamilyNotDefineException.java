/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : ColumFamilyNotDefineException.java
 */
package com.dhc.github.framework.exception;

/**
 * 功能描述:  <p>
 *
 * @author : huichaodeng <p>
 * @version 1.0 2017-08-28
 * @since horm 1.0
 */
public class ColumFamilyNotDefineException extends HomException{

    public ColumFamilyNotDefineException(){
        super();
    }

    public ColumFamilyNotDefineException(String message) {
        super(message);
    }

    public ColumFamilyNotDefineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumFamilyNotDefineException(Throwable cause) {
        super(cause);
    }
}
