package com.jic.elearning.thrid.exception;


/**
 * Created by lee5hx on 2017/10/27.
 */


public class TaoBaoApiErrorException extends Exception {

    public TaoBaoApiErrorException() {
        super();
    }

    public TaoBaoApiErrorException(String message) {
        super(message);
    }

    public TaoBaoApiErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaoBaoApiErrorException(Throwable cause) {
        super(cause);
    }

    protected TaoBaoApiErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
