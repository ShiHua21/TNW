package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lee5hx
 */
@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.org.not.exists")
public class OrgNotExistsException extends RuntimeException {
}
