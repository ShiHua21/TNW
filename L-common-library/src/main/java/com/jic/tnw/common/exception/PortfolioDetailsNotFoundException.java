package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


@ResponseStatus(code= HttpStatus.NOT_FOUND,reason="portfolio.details.notFound")
public class PortfolioDetailsNotFoundException extends Exception {

//    public UserNameExistsException() {
//        super();
//    }
//
//    public UserNameExistsException(String message) {
//        super(message);
//    }
//
//    public UserNameExistsException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public UserNameExistsException(Throwable cause) {
//        super(cause);
//    }
//
//    protected UserNameExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}