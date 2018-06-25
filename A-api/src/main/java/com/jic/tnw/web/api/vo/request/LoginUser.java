package com.jic.tnw.web.api.vo.request;

import java.time.LocalDateTime;

/**
 * Created by lee5hx on 2017/10/27.
 */
public class LoginUser {


    private String email;
    private String username;
    private LocalDateTime createdtime;
    private Boolean answerd;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public LocalDateTime getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(LocalDateTime createdtime) {
        this.createdtime = createdtime;
    }


    public Boolean getAnswerd() {
        return answerd;
    }

    public void setAnswerd(Boolean answerd) {
        this.answerd = answerd;
    }
}
