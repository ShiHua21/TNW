package com.jic.tnw.web.api.vo.request.user;

import org.hibernate.validator.constraints.NotBlank;

public class UserSetName {
    @NotBlank(message = "{user.username.not.blank}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
