package com.jic.elearning.thrid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.METHOD_NOT_ALLOWED,reason="验证码失效")
public class VerifyCodeInvalidException extends RuntimeException{
}
