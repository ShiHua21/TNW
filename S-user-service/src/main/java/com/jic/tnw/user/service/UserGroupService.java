package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.tables.pojos.UserGroup;
import com.jic.tnw.db.mysql.tables.pojos.UserGroupMember;
import com.jic.tnw.user.service.dto.MatchMember;

import java.util.List;

public interface UserGroupService {


    UserGroupMember addUserGroupMember(UserGroupMember addUserGroupMember);

    UserGroupMember deleteUserGroupMember(Integer Id);

    List<UserGroup> findUserGroups();

    UserGroup findById(Integer id);

    UserGroup addUserGroup(UserGroup addUserGroup);

    UserGroup editUserGroup(UserGroup editUserGroup);

    UserGroup deleteById(Integer id);

    UserGroupMember findUserGroupMemberById(Integer id);

    List<UserGroupMember> findMemberByGroupId(Integer groupId);

    UserGroupMember findUserGroupMemberByMemberIdAndType(Integer groupId,Integer memberId, UserGroupMemberMemberType memberType);

    List<MatchMember> searchMemberByUser(String name);

    List<MatchMember> searchMemberByOrg(String name);

    List<MatchMember> searchMemberByPost(String name);
}
