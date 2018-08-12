package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.mobile.exists")
public class UserMobileExistsException extends RuntimeException {

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
