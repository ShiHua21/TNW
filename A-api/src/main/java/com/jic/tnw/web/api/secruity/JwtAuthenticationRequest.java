package com.jic.tnw.web.api.secruity;

import java.io.Serializable;

public class  JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String principal;
    private String password;

    public JwtAuthenticationRequest(String principal, String password) {
        this.setPrincipal(principal);
        this.setPassword(password);
    }

    /**
     * Please keep this constructor as it will be
     */
    public JwtAuthenticationRequest() {
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
