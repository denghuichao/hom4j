/*
 * Create Author  : huichaodeng
 * Create Date    : 2017-08-28
 * Project        : horm
 * File Name      : ColumFamilyNotDefineException.java
 */
package com.github.dhc.framework.hom.exception;

/**
 * @author : huichaodeng
 * @version 1.0 2017-08-28
 * @since hom 1.0
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
