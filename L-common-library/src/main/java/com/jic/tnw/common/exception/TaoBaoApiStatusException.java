package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


@ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR,reason="淘宝接口服务异常,请检查!(可能需要重新登录,操作发货接口)")
public class TaoBaoApiStatusException extends Exception {

    public TaoBaoApiStatusException() {
        super();
    }

    public TaoBaoApiStatusException(String message) {
        super(message);
    }

    public TaoBaoApiStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaoBaoApiStatusException(Throwable cause) {
        super(cause);
    }

    protected TaoBaoApiStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
