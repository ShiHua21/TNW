package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lee5hx on 2017/10/27.
 */


@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.org.code.exists")
public class OrgCodeExistsException extends RuntimeException {

}
