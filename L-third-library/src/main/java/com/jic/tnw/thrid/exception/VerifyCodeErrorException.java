package com.jic.tnw.thrid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.PAYMENT_REQUIRED,reason="手机验证码错误")
public class VerifyCodeErrorException extends RuntimeException{
}
