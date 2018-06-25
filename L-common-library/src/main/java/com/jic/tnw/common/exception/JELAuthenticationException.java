package com.jic.tnw.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by lee5hx on 2018/2/8.
 */
public class JELAuthenticationException extends AuthenticationException {

    public JELAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JELAuthenticationException(String msg) {
        super(msg);
    }
}
