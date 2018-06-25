package com.jic.tnw.user.service.dto;

import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;

/**
 * Created by lee5hx on 2018/3/28.
 */
public class MatchMember {

    private String memberId;
    private String memberName;
    private UserGroupMemberMemberType userGroupMemberMemberType;
    private Long peopleNumber;
    private String memberTrueName;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public UserGroupMemberMemberType getUserGroupMemberMemberType() {
        return userGroupMemberMemberType;
    }

    public void setUserGroupMemberMemberType(UserGroupMemberMemberType userGroupMemberMemberType) {
        this.userGroupMemberMemberType = userGroupMemberMemberType;
    }

    public Long getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Long peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getMemberTrueName() {
        return memberTrueName;
    }

    public void setMemberTrueName(String memberTrueName) {
        this.memberTrueName = memberTrueName;
    }
}


