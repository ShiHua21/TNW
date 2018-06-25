package com.jic.tnw.web.api.vo.response.user.group;

import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author lee5hx
 * @date 2018/03/26
 */
public class UserGroupMemberResource extends ResourceSupport {
    private Integer userGroupMemberId;
    private Integer groupId;
    private Integer memberId;
    private UserGroupMemberMemberType memberType;
    private String memberName;
    private Long peopleNumber;

    public Long getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Long peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public UserGroupMemberMemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(UserGroupMemberMemberType memberType) {
        this.memberType = memberType;
    }

    public Integer getUserGroupMemberId() {
        return userGroupMemberId;
    }

    public void setUserGroupMemberId(Integer userGroupMemberId) {
        this.userGroupMemberId = userGroupMemberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
