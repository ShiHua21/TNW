package com.jic.tnw.web.api.vo.request.user.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import org.springframework.validation.annotation.Validated;

/**
 * 新增用户组
 * @author lee5hx
 * @date 2018/03/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddUserGroupMember {

    private String memberId;

    private UserGroupMemberMemberType userGroupMemberMemberType;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public UserGroupMemberMemberType getUserGroupMemberMemberType() {
        return userGroupMemberMemberType;
    }

    public void setUserGroupMemberMemberType(UserGroupMemberMemberType userGroupMemberMemberType) {
        this.userGroupMemberMemberType = userGroupMemberMemberType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddUserGroupMember (");
        sb.append(memberId);
        sb.append(", ").append(userGroupMemberMemberType);
        sb.append(")");
        return sb.toString();
    }
}
