package com.jic.tnw.web.api.vo.response.user;

import org.springframework.hateoas.ResourceSupport;

/**
 * 返回新增用户Resource
 * @author lee5hx
 * @date 2018/03/19
 */
public class AddUserResource extends ResourceSupport {

    private String truename;

    private String email;

    private String username;

    private String orgIds;

    private String postId;

    private String roles;

    private Integer uid;

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

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
