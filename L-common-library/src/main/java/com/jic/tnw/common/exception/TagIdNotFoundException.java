package com.jic.tnw.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tp
 * @date 2018/4/2
 */

@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="course.tag.id.not.found")
public class TagIdNotFoundException extends RuntimeException {
}
