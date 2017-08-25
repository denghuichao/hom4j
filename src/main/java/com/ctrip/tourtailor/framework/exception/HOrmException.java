package com.ctrip.tourtailor.framework.exception;

/**
 * Created by hcdeng on 17-8-25.
 * HORM框架向外抛出异常
 */
public class HOrmException extends Exception{

    public HOrmException(){
        super();
    }

    public HOrmException(String message) {
        super(message);
    }

    public HOrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public HOrmException(Throwable cause) {
        super(cause);
    }
}
