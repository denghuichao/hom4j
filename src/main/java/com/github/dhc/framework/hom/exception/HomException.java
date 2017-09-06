package com.github.dhc.framework.hom.exception;

/**
 * Created by hcdeng on 17-8-25.
 * Base exception the hom framework throws out
 */
public class HomException extends RuntimeException{

    public HomException(){
        super();
    }

    public HomException(String message) {
        super(message);
    }

    public HomException(String message, Throwable cause) {
        super(message, cause);
    }

    public HomException(Throwable cause) {
        super(cause);
    }
}
