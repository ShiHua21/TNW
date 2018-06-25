package com.jic.tnw.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.UserGroupCodeAlreadyOccupiedException;
import com.jic.tnw.common.exception.UserGroupCodeExistsException;
import com.jic.tnw.common.exception.UserGroupNotExistsException;
import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.db.repository.*;
import com.jic.tnw.user.service.dto.MatchMember;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee5hx on 2017/10/19.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private static final Log LOGGER = LogFactory.getLog(UserGroupServiceImpl.class);

    private final ObjectMapper mapper;
    private final UserGroupRepository userGroupRepository;
    private final UserGroupMemberRepository userGroupMemberRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;
    private final UserService userService;
    private final RedisTemplate redisTemplate;

    @Autowired
    UserGroupServiceImpl(ObjectMapper objectMapper,
                         UserGroupRepository userGroupRepository,
                         UserGroupMemberRepository userGroupMemberRepository,
                         PostRepository postRepository,
                         UserRepository userRepository,
                         OrgRepository orgRepository,
                         UserService userService, RedisTemplate redisTemplate) {
        this.mapper = objectMapper;
        this.userGroupRepository = userGroupRepository;
        this.userGroupMemberRepository = userGroupMemberRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<UserGroup> findUserGroups() {
        return userGroupRepository.findAll();
    }

    @Override
    public UserGroup findById(Integer id) {
        return userGroupRepository.findById(id);
    }

    @Override
    public UserGroupMember findUserGroupMemberById(Integer id) {
        return userGroupMemberRepository.findById(id);
    }

    @Override
    public UserGroupMember findUserGroupMemberByMemberIdAndType(Integer groupId,Integer memberId,
                                                                                       UserGroupMemberMemberType memberType){
        return userGroupMemberRepository.findByMemberIdAndType(groupId,memberId,memberType);
    }

    @Override
    public List<UserGroupMember> findMemberByGroupId(Integer groupId) {
        return userGroupMemberRepository.findByGroupId(groupId);
    }


    @Override
    public UserGroup deleteById(Integer id) {
        return userGroupRepository.delete(id);
    }


    @Override
    public UserGroup addUserGroup(UserGroup addUserGroup) {
        UserGroup userGroup = userGroupRepository.findByCode(addUserGroup.getCode());
        if (userGroup != null) {
            throw new UserGroupCodeExistsException();
        }
        userGroup = new UserGroup();
        userGroup.setCode(addUserGroup.getCode());
        userGroup.setDescription(addUserGroup.getDescription());
        userGroup.setName(addUserGroup.getName());
        userGroup.setCreatedUserId(addUserGroup.getCreatedUserId());
        userGroup.setCreatedTime(LocalDateTime.now());
        return userGroupRepository.add(userGroup);
    }

    @Override
    public UserGroupMember addUserGroupMember(UserGroupMember addUserGroupMember) {
        return userGroupMemberRepository.add(addUserGroupMember);
    }


    @Override
    public UserGroupMember deleteUserGroupMember(Integer Id) {
        return userGroupMemberRepository.delete(Id);
    }




    @Override
    public UserGroup editUserGroup(UserGroup editUserGroup) {

        LOGGER.info(String.format("User.Group Id=%d Edit %s",
                editUserGroup.getId(),
                editUserGroup.toString()
        ));
        Integer userGroupId = editUserGroup.getId();

        LOGGER.debug(String.format("UserGroup-Id=%d", userGroupId));
        UserGroup userGroup = userGroupRepository.findById(userGroupId);
        if (userGroup == null) {
            throw new UserGroupNotExistsException();
        }
        UserGroup codeUserGroup = userGroupRepository.findByCode(editUserGroup.getCode());
        if ((!editUserGroup.getCode().equals(userGroup.getCode())) && codeUserGroup != null) {
            //编码已被占用,请换一个
            throw new UserGroupCodeAlreadyOccupiedException();
        }
        return userGroupRepository.update(editUserGroup);
    }


    @Override
    public List<MatchMember> searchMemberByUser(String name) {
        List<User> users = userRepository.findByContainsTrueName(name);
        List<MatchMember> matchMembers = new ArrayList<>();
        MatchMember matchMember;
        for (User user : users) {
            matchMember = new MatchMember();
            matchMember.setMemberId(String.valueOf(user.getId()));
            matchMember.setMemberName(user.getUsername());
            matchMember.setMemberTrueName(user.getTruename());
            matchMember.setPeopleNumber(0L);
            matchMember.setUserGroupMemberMemberType(UserGroupMemberMemberType.user);
            matchMembers.add(matchMember);
        }
        return matchMembers;
    }

    @Override
    public List<MatchMember> searchMemberByOrg(String name) {
        List<MatchMember> matchMembers = new ArrayList<>();
        MatchMember matchMember;
        List<Org> list = orgRepository.findByContainsName(name);
        for (Org org : list) {
            matchMember = new MatchMember();
            matchMember.setMemberId(String.valueOf(org.getId()));
            matchMember.setMemberName(org.getName());
            matchMember.setMemberTrueName("");
            matchMember.setPeopleNumber(userService.countByOrg(org.getId()));
            matchMember.setUserGroupMemberMemberType(UserGroupMemberMemberType.org);
            matchMembers.add(matchMember);
        }
        return matchMembers;
    }

    @Override
    public List<MatchMember> searchMemberByPost(String name) {
        List<MatchMember> matchMembers = new ArrayList<>();
        MatchMember matchMember;
        List<Post> list = postRepository.findByContainsName(name);
        for (Post post : list) {
            matchMember = new MatchMember();
            matchMember.setMemberId(String.valueOf(post.getId()));
            matchMember.setMemberName(post.getName());
            matchMember.setMemberTrueName("");
            matchMember.setPeopleNumber(userService.countByPost(post.getId()));
            matchMember.setUserGroupMemberMemberType(UserGroupMemberMemberType.post);
            matchMembers.add(matchMember);
        }
        return matchMembers;

    }
}
