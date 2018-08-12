package com.jic.tnw.thrid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.METHOD_NOT_ALLOWED,reason="手机验证码失效")
public class VerifyCodeInvalidException extends RuntimeException{
}
