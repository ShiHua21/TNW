package com.jic.tnw.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="user.post.group.cannot.del")
public class PostGroupCanNotDelException extends RuntimeException {
}
