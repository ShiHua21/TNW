package com.jic.tnw.web.api.vo.response.user.group;

import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author lee5hx
 * @date 2018/03/28
 */
public class MemberMatchResource extends ResourceSupport {
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
