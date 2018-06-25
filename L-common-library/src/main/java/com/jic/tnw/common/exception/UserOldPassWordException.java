package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="旧密码错误!")
public class UserOldPassWordException extends Exception {

    public UserOldPassWordException() {
        super();
    }

    public UserOldPassWordException(String message) {
        super(message);
    }

    public UserOldPassWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserOldPassWordException(Throwable cause) {
        super(cause);
    }

    protected UserOldPassWordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
