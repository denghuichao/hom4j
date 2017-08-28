package com.dhc.github.framework.exception;

/**
 * Created by hcdeng on 17-8-25.
 * HORM框架向外抛出的异常
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
