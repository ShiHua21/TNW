package com.jic.tnw.user.service.dto.post;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lee5hx on 2018/4/9.
 */
public class AddPostGroupDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
