package com.jic.tnw.web.api.vo.request.user;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;

@Validated
public class UserSetPost {

    @NotNull(message = "{user.postid.not.empty}")
//    @Pattern(regexp="^[0-9]{1,2}$",message="年龄不正确")
    private Integer postid;

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }
}
