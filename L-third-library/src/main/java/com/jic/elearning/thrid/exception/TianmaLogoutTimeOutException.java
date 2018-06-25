package com.jic.elearning.thrid.exception;

/**
 * Created by lee5hx on 2017/12/4.
 */
public class TianmaLogoutTimeOutException extends Exception {

    public TianmaLogoutTimeOutException() {
        super();
    }

    public TianmaLogoutTimeOutException(String message) {
        super(message);
    }

    public TianmaLogoutTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TianmaLogoutTimeOutException(Throwable cause) {
        super(cause);
    }

    protected TianmaLogoutTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
