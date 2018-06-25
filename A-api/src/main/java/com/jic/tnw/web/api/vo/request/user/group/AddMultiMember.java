package com.jic.tnw.web.api.vo.request.user.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * 新增用户组
 * @author lee5hx
 * @date 2018/03/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddMultiMember {

    private AddUserGroupMember[] addUserGroupMembers;


    public AddUserGroupMember[] getAddUserGroupMembers() {
        return addUserGroupMembers;
    }

    public void setAddUserGroupMembers(AddUserGroupMember[] addUserGroupMembers) {
        this.addUserGroupMembers = addUserGroupMembers;
    }
}
