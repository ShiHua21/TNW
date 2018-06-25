package com.jic.tnw.web.api.auth;


import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author lee5hx
 */
public interface AuthService {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param ip
     * @return
     */
    UserDetails login(String username, String password, String ip);

    /**
     * 刷新令牌
     *
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);
}
