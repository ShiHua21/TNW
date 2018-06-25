package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


@ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR,reason="天马接口服务异常,请检查!(可能需要重新登录)")
public class TianMaApiStatusException extends Exception {

    public TianMaApiStatusException() {
        super();
    }

    public TianMaApiStatusException(String message) {
        super(message);
    }

    public TianMaApiStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public TianMaApiStatusException(Throwable cause) {
        super(cause);
    }

    protected TianMaApiStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
