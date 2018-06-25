package com.jic.tnw.web.api.vo.request.post;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lee5hx on 2018/3/5.
 */
public class CreatePostGroup {

    @NotBlank(message = "{user.post.group.name.not.blank}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
