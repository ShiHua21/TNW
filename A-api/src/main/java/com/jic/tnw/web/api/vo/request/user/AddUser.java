package com.jic.tnw.web.api.vo.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.Pattern;

/**
 * 新增用户
 * @author lee5hx
 * @date 2018/03/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddUser{

    /**
     * name
     * user.truename.not.blank = 姓名不能为空!
     */
    @NotBlank(message = "{user.truename.not.blank}")
    private String truename;

    /**
     * email
     * user.email.not.blank = 邮箱不能为空!
     */
    @NotBlank(message = "{user.email.not.blank}")
    private String email;

    /**
     * username
     * user.username.not.blank = 用户名不能为空!
     */
    @NotBlank(message = "{user.username.not.blank}")
    private String username;

    /**
     * password
     * user.password.not.blank = 密码不能为空!
     */
    @NotBlank(message = "{user.password.not.blank}")
    private String password;

    /**
     * q2password
     * user.q2password.not.blank = 确认密码不能为空!
     */
    @NotBlank(message = "{user.q2password.not.blank}")
    private String q2password;


    @NotEmpty(message = "{user.orgIds.not.blank}")
    private String[] orgIds;


    @NotBlank(message = "{user.postId.not.blank}")
    private String postId;


    @NotEmpty(message = "{user.roles.not.blank}")
    private String[] roles;


    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQ2password() {
        return q2password;
    }

    public void setQ2password(String q2password) {
        this.q2password = q2password;
    }

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }


    public <T> T toDTO(Converter<AddUser,T> converter){
        return converter.convert(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddUser (");
        sb.append(truename);
        sb.append(", ").append(email);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(q2password);
        sb.append(", ").append(orgIds);
        sb.append(", ").append(postId);
        sb.append(", ").append(roles);
        sb.append(")");
        return sb.toString();
    }
}
