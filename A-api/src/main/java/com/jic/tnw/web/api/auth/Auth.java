package com.jic.tnw.web.api.auth;



import com.jic.elearning.web.api.vo.request.LoginUser;
import com.jic.tnw.web.api.vo.request.LoginUser;

/**
 * @author lee5hx
 */
public class Auth {
    private String token;
    private LoginUser user;


    public Auth(String token, LoginUser user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }
}
