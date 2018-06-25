package com.jic.tnw.web.api.vo.response.user.group;

import org.springframework.hateoas.ResourceSupport;

/**
 * 返回新增用户Resource
 * @author lee5hx
 * @date 2018/03/19
 */
public class UserGroupResource extends ResourceSupport {
    private Integer userGroupId;
    private String name;
    private String code;
    private String description;
    private Integer peopleNumber;

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
