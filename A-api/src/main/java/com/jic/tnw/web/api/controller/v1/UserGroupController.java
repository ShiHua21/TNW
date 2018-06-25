package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
//import UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.elearning.user.service.*;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.PostService;
import com.jic.tnw.user.service.UserGroupService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.user.service.dto.MatchMember;
import com.jic.elearning.web.api.vo.request.user.group.AddMultiMember;
import com.jic.elearning.web.api.vo.request.user.group.AddUserGroup;
import com.jic.elearning.web.api.vo.request.user.group.AddUserGroupMember;
import com.jic.elearning.web.api.vo.request.user.group.EditUserGroup;
import com.jic.tnw.common.exception.*;
import com.jic.tnw.web.api.vo.request.user.group.AddMultiMember;
import com.jic.tnw.web.api.vo.request.user.group.AddUserGroup;
import com.jic.tnw.web.api.vo.request.user.group.AddUserGroupMember;
import com.jic.tnw.web.api.vo.request.user.group.EditUserGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author lee5hx
 * @date 2018/3/21
 */
@RestController
@RequestMapping("/v1")
@Api(description = "用户组管理", tags = {"F-用户模块-4"})
public class UserGroupController {

    private static final Log LOGGER = LogFactory.getLog(UserGroupController.class);
    private final LocaleMessageSourceService localeMessageSourceService;
    private final ObjectMapper mapper;
    private final UserGroupService userGroupService;
    private final UserService userService;
    private final PostService postService;
    private final OrgService orgService;
    private UserGroupResourceAssembler userGroupResourceAssembler = new UserGroupResourceAssembler(UserGroupController.class, UserGroupResource.class);
    private UserGroupMemberResourceAssembler userGroupMemberResourceAssembler = new UserGroupMemberResourceAssembler(UserGroupController.class, UserGroupMemberResource.class);
    private MemberMatchResourceAssembler memberMatchResourceAssembler = new MemberMatchResourceAssembler(UserGroupController.class, MemberMatchResource.class);


    @Autowired
    public UserGroupController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,
            UserGroupService userGroupService,
            UserService userService,
            PostService postService,
            OrgService orgService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.userService = userService;
        this.userGroupService = userGroupService;
        this.postService = postService;
        this.orgService = orgService;
    }

    @RequestMapping(value = "/userGroups", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回用户组", notes = "返回用户组列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getUserGroup() throws Exception {
        //UserGroupResourceAssembler userGroupResourceAssembler = new UserGroupResourceAssembler(UserGroupController.class, UserGroupResource.class);
        List<UserGroup> list = userGroupService.findUserGroups();

        List<UserGroupResource> resources = userGroupResourceAssembler.toResources(list);
        Resources<UserGroupResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(UserGroupController.class).addUserGroup(null, null)).withRel("post_add_user_group")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }

    @RequestMapping(value = "/userGroup/{id}/member/match", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "匹配成员列表", notes = "匹配成员列表(用户,组织,岗位)", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "query", value = "查询关键字", dataType = "String", paramType = "query"),

    })
    public ResponseEntity<?> getUserGroupMemberMatch(@PathVariable Integer id, String query) throws Exception {
        LOGGER.info(String.format("group.id = %d, query = %s", id, query));
        List<MatchMember> list = new ArrayList<>();
        List<UserGroupMember> members = userGroupService.findMemberByGroupId(id);
        Map<String, UserGroupMember> map = members.stream()
                .collect(
                        Collectors.toMap(o -> o.getMemberId().toString() + ":" + o.getMemberType(), Function.identity())
                );

        //xxx 查询 待优化 by lee5hx
        list.addAll(userGroupService.searchMemberByUser(query));
        list.addAll(userGroupService.searchMemberByPost(query));
        list.addAll(userGroupService.searchMemberByOrg(query));

        //过滤已经存在的成员
        list = list.stream()
                .filter(matchMember -> {
                    boolean rt = map.containsKey(matchMember.getMemberId().toString() + ":" + matchMember.getUserGroupMemberMemberType());
                    return !rt;
                })
                .collect(Collectors.toList());

        List<MemberMatchResource> resources = memberMatchResourceAssembler.toResources(list);
        Resources<MemberMatchResource> wrapped = new Resources<>(
                resources
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }


    @RequestMapping(value = "/userGroup", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "新增用户分组", notes = "新增用户分组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addUserGroup", value = "新增用户组", required = true, dataType = "AddUserGroup")
    })
    public ResponseEntity<?> addUserGroup(@Validated @RequestBody AddUserGroup addUserGroup, @AuthenticationPrincipal UserDetails user) throws Exception {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(addUserGroup.getName());
        userGroup.setCode(addUserGroup.getCode());
        userGroup.setDescription(addUserGroup.getDescription());
        userGroup.setCreatedUserId(Integer.parseInt(user.getUsername()));
        UserGroupResource userGroupResource = userGroupResourceAssembler
                .toResource(userGroupService.addUserGroup(userGroup));
        return new ResponseEntity<>(userGroupResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/userGroup/{id}", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "根据ID获取用户分组信息", notes = "根据ID获取用户分组信息", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getUserGroup(@PathVariable Integer id) throws Exception {
        //UserGroupResourceAssembler userGroupResourceAssembler = new UserGroupResourceAssembler(UserGroupController.class, UserGroupResource.class);
        UserGroup userGroup = userGroupService.findById(id);
        if (userGroup == null) {
            throw new UserGroupNotExistsException();
        }
        UserGroupResource userGroupResource = userGroupResourceAssembler.toResource(userGroup);
        return new ResponseEntity<>(userGroupResource, HttpStatus.OK);

    }

    @RequestMapping(value = "/userGroup/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户分组", notes = "根据分组ID修改用户分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editUserGroup", value = "修改用户组", required = true, dataType = "EditUserGroup")
    })
    public ResponseEntity<?> editUserGroup(@PathVariable Integer id, @Validated @RequestBody EditUserGroup editUserGroup, @AuthenticationPrincipal UserDetails user) throws Exception {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setName(editUserGroup.getName());
        userGroup.setCode(editUserGroup.getCode());
        userGroup.setDescription(editUserGroup.getDescription());
        userGroup.setLastUpdUserId(Integer.parseInt(user.getUsername()));
        userGroup.setLastUpdTime(LocalDateTime.now());
        userGroup = userGroupService.editUserGroup(userGroup);
        UserGroupResource userGroupResource = userGroupResourceAssembler.toResource(userGroup);
        return ResponseEntity.ok(userGroupResource);
    }

    @RequestMapping(value = "/userGroup/{id}", method = RequestMethod.DELETE, produces = "application/hal+json")
    @ApiOperation(value = "删除分组", notes = "根据机构ID删除用户分组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> deleteUserGroup(@PathVariable Integer id) throws Exception {
        UserGroupResource userGroupResource = userGroupResourceAssembler.toResource(userGroupService.deleteById(id));
        return new ResponseEntity<>(userGroupResource, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/userGroup/{id}/members", method = RequestMethod.GET)
    @ApiOperation(value = "根据分组ID,获取分组成员列表", notes = "根据分组ID,获取分组成员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getMember(@PathVariable Integer id) throws Exception {
        List<UserGroupMember> list = userGroupService.findMemberByGroupId(id);
        List<UserGroupMemberResource> resources = userGroupMemberResourceAssembler.toResources(list);
        Post post;
        Org org;
        User user;
        for (UserGroupMemberResource userGroupMemberResource : resources) {
            if (userGroupMemberResource.getMemberType() == UserGroupMemberMemberType.org) {
                org = orgService.getOrg(userGroupMemberResource.getMemberId());
                userGroupMemberResource.setMemberName(org.getName());
                userGroupMemberResource.setPeopleNumber(userService.countByOrg(userGroupMemberResource.getMemberId()));
            }
            if (userGroupMemberResource.getMemberType() == UserGroupMemberMemberType.post) {
                post = postService.getPost(userGroupMemberResource.getMemberId());
                userGroupMemberResource.setMemberName(post.getName());
                userGroupMemberResource.setPeopleNumber(userService.countByPost(userGroupMemberResource.getMemberId()));
            }
            if (userGroupMemberResource.getMemberType() == UserGroupMemberMemberType.user) {
                JelUser jelUser= userService.findById(userGroupMemberResource.getMemberId());
                userGroupMemberResource.setMemberName(jelUser.getUser().getTruename());
                userGroupMemberResource.setPeopleNumber(1L);
            }
        }

        Resources<UserGroupMemberResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(UserGroupController.class).addMember(id, null, null)).withRel("post_add_member"),
                linkTo(methodOn(UserGroupController.class).getUserGroupMemberMatch(id, null)).withRel("get_query_match_member")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }

    @RequestMapping(value = "/userGroup/{id}/member", method = RequestMethod.POST)
    @ApiOperation(value = "根据分组ID,增加成员", notes = "根据分组ID,增加成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> addMember(@PathVariable Integer id,
                                       @Validated @RequestBody AddMultiMember addMultiMember,
                                       @AuthenticationPrincipal UserDetails user) throws Exception {
        UserGroup userGroup = userGroupService.findById(id);
        if (userGroup == null) {
            throw new UserGroupNotExistsException();
        }
        AddUserGroupMember[] addUserGroupMembers = addMultiMember.getAddUserGroupMembers();
        List<UserGroupMemberResource> resources = new ArrayList<>();
        UserGroupMember userGroupMember;

        Post post;
        Org org;
        for (AddUserGroupMember addUserGroupMember : addUserGroupMembers) {
            userGroupMember = userGroupService.findUserGroupMemberByMemberIdAndType(
                    id,
                    Integer.valueOf(addUserGroupMember.getMemberId()),
                    addUserGroupMember.getUserGroupMemberMemberType()
            );
            //如果成员已经存在，不必要进行新增了
            if (userGroupMember == null) {
                userGroupMember = new UserGroupMember();
                if (addUserGroupMember.getUserGroupMemberMemberType() == UserGroupMemberMemberType.org) {
                    org = orgService.getOrg(Integer.valueOf(addUserGroupMember.getMemberId()));
                    if (org == null) {
                        throw new OrgNotExistsException();
                    }
                }
                if (addUserGroupMember.getUserGroupMemberMemberType() == UserGroupMemberMemberType.post) {
                    post = postService.getPost(Integer.valueOf(addUserGroupMember.getMemberId()));
                    if (post == null) {
                        throw new PostNotFoundException();
                    }
                }
                userGroupMember.setMemberId(Integer.valueOf(addUserGroupMember.getMemberId()));
                userGroupMember.setMemberType(addUserGroupMember.getUserGroupMemberMemberType());
                userGroupMember.setGroupId(id);
                userGroupMember.setCreatedTime(LocalDateTime.now());
                userGroupMember.setCreatedUserId(Integer.valueOf(user.getUsername()));
                userGroupMember = userGroupService.addUserGroupMember(userGroupMember);
            }
            UserGroupMemberResource userGroupMemberResource = userGroupMemberResourceAssembler
                    .toResource(userGroupMember);
            resources.add(userGroupMemberResource);
        }
        Resources<MemberMatchResource> wrapped = new Resources(
                resources
        );
        return new ResponseEntity<>(wrapped, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/userGroup/{id}/member/{mid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据分组ID与成员ID，进行移除成员", notes = "根据分组ID与成员ID，进行移除成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分组ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "mid", value = "成员ID", required = true, paramType = "path", dataType = "int")

    })
    public ResponseEntity<?> deleteMember(@PathVariable Integer id, @PathVariable Integer mid) throws Exception {
        UserGroup userGroup = userGroupService.findById(id);
        if (userGroup == null) {
            throw new UserGroupNotExistsException();
        }
        UserGroupMember userGroupMember = userGroupService.findUserGroupMemberById(mid);
        if (userGroupMember == null) {
            throw new UserGroupMemberNotExistsException();
        }
        if (!userGroup.getId().equals(userGroupMember.getGroupId())) {
            throw new UserGroupMemberNotGroupException();
        }
        UserGroupMemberResource userGroupMemberResource = userGroupMemberResourceAssembler
                .toResource(userGroupService.deleteUserGroupMember(mid));
        return new ResponseEntity<>(userGroupMemberResource, HttpStatus.NO_CONTENT);
    }


}
