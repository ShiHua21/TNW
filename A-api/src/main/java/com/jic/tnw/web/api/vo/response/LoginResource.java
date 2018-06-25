package com.jic.tnw.web.api.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lee5hx on 2017/10/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginResource extends ResourceSupport {

    private String token;
    private String roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
