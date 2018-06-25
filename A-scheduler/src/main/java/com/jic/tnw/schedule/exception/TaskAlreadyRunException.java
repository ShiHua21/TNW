package com.jic.tnw.schedule.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */

@ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR,reason="任务已经运行")
public class TaskAlreadyRunException extends Exception {

    public TaskAlreadyRunException() {
        super();
    }

    public TaskAlreadyRunException(String message) {
        super(message);
    }

    public TaskAlreadyRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskAlreadyRunException(Throwable cause) {
        super(cause);
    }

    protected TaskAlreadyRunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
