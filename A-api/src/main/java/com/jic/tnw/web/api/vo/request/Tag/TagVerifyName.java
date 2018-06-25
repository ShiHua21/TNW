package com.jic.tnw.web.api.vo.request.Tag;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author tp
 * @date 2018/4/2
 */
public class TagVerifyName {
    @NotBlank (message = "{course.tag.name.not.blank}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
