package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


//@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.org.name.exists")
@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.not.exists")
public class UserNotExistsException extends RuntimeException {

//    public UserNameExistsException() {
//        super();
//    }x3
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
