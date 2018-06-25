package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.profile.not.found")

public class UserProfileNotexistException extends RuntimeException {
}
