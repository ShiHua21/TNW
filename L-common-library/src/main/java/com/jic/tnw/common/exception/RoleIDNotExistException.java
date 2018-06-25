package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.METHOD_NOT_ALLOWED,reason="user.role.id.not.found")

public class RoleIDNotExistException extends RuntimeException{
}
