package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.tables.pojos.UserGroupMember;

import java.util.List;

/**
 * @author lee5hx
 */
public interface UserGroupMemberRepository extends JooqRepository<UserGroupMember> {
    List<UserGroupMember> findByGroupId(Integer groupId);
    UserGroupMember findByMemberIdAndType(Integer groupId,Integer memberId,UserGroupMemberMemberType memberType);
}
