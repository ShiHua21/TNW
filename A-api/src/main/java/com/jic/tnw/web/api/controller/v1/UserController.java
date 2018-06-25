package com.jic.tnw.web.api.controller.v1;

import com.jic.tnw.db.mysql.tables.pojos.Org;
import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.PostService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.AddUserDTO;
import com.jic.tnw.user.service.dto.UserSetRoleGroup;
import com.jic.tnw.user.service.dto.user.EditUserInfoDTO;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.vo.response.user.JelUserResourceAssembler;
import com.jic.elearning.web.api.vo.response.user.UserCountResource;
import com.jic.elearning.web.api.vo.response.user.UserResource;
import com.jic.elearning.web.api.vo.response.user.UserSetOrgResource;
import com.jic.tnw.common.exception.*;
import com.jic.tnw.web.api.vo.request.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author lee5hx
 */
@RestController
@RequestMapping("/v1")
@Api(description = "用户管理", tags = {"F-用户模块-1"})
public class UserController {

    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    private final LocaleMessageSourceService localeMessageSourceService;

    private final UserService userService;

    private final PostService postService;


    private final OrgService orgService;
    private final JelUserResourceAssembler jelUserResourceAssembler = new JelUserResourceAssembler(UserController.class, UserResource.class);


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserController(
            LocaleMessageSourceService localeMessageSourceService,
            UserService userService,
            PostService postService,
            OrgService orgService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.userService = userService;
        this.postService = postService;
        this.orgService = orgService;
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addUser", value = "新增用户", required = true, dataType = "AddUser")

    })
    public ResponseEntity<?> addUser(@Validated @RequestBody AddUser addUser, @AuthenticationPrincipal UserDetails
            user) throws Exception {
        LOGGER.debug(String.format("%s", addUser.toString()));
        if (!addUser.getQ2password().equals(addUser.getPassword())) {
            //2次密码不一致
            throw new TwoDifferentPasswordException();
        }

        User exUser = userService.findByName(addUser.getUsername());
        if (exUser != null) {
            throw new UserNameExistsException();
        }

        exUser = userService.findByEmail(addUser.getEmail());
        if (exUser != null) {
            throw new UserEmailExistsException();
        }
//        AddUserResource addUserResource = new AddUserResource();
        AddUserDTO addUserDTO = addUser.toDTO(source -> {
            AddUserDTO aud = new AddUserDTO();
            aud.setEmail(source.getEmail());
            aud.setOrgIds(source.getOrgIds());
            aud.setPassword(passwordEncoder.encode(source.getQ2password()));
            aud.setPostId(source.getPostId());
            aud.setUsername(source.getUsername());
            aud.setTruename(source.getTruename());
            aud.setRoles(source.getRoles());
            aud.setUid(Integer.parseInt(user.getUsername()));
            return aud;
        });
        addUserDTO.setType("admin_add");
        JelUser jelUser = userService.add(addUserDTO);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "返回用户列表", notes = "返回用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "org", value = "机构", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "post", value = "岗位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "trueName", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginIp", value = "登陆IP", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "promoted", value = "是否推荐", dataType = "String", paramType = "query")
    })
    public ResponseEntity<?> getUsers(
            String org,
            String post,
            String role,
            String userName,
            String trueName,
            String mobile,
            String email,
            String loginIp,
            String promoted,
            Pageable pageable,
            PagedResourcesAssembler assembler) throws Exception {
        Map<String, Object> conditionMap = new HashMap<>(10);

        if (!StringUtils.isEmpty(org)) {
            conditionMap.put("org", org);
        }
        if (!StringUtils.isEmpty(post)) {
            conditionMap.put("post", post);
        }
        if (!StringUtils.isEmpty(role)) {
            conditionMap.put("role", role);
        }
        if (!StringUtils.isEmpty(userName)) {
            conditionMap.put("user_name", userName);
        }
        if (!StringUtils.isEmpty(trueName)) {
            conditionMap.put("true_name", trueName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            conditionMap.put("mobile", mobile);
        }
        if (!StringUtils.isEmpty(email)) {
            conditionMap.put("email", email);
        }
        if (!StringUtils.isEmpty(loginIp)) {
            conditionMap.put("loginIp", loginIp);
        }
        if (!StringUtils.isEmpty(promoted)) {
            conditionMap.put("promoted", promoted);
        }

        Map<Integer, Org> orgMap = orgService.getOrgMap();
        Page<User> page = userService.findWithPageable(pageable, conditionMap);

        Page<UserResource> newPage = page.map(source -> {
            UserResource userResource = new UserResource();
            userResource.setUserId(source.getId());
            userResource.setUserName(source.getUsername());
            userResource.setLastLoginIp(source.getLoginIp());
            userResource.setLastLoginTime(source.getLoginTime());
            userResource.setOrgIds(source.getOrgIds());
            userResource.setLocked(source.getLocked());

            String orgNames = Arrays.stream(userResource.getOrgIds().split("\\|"))
                    .map(s -> {
                        if (orgMap.containsKey(Integer.valueOf(s))) {
                            return orgMap.get(Integer.valueOf(s)).getName();
                        } else {
                            return "0";
                        }
                    })
                    .reduce((acc, item) -> String.format("%s|%s", acc, item)).get();

            userResource.setOrgNames(orgNames);
            userResource.setPostId(source.getPostId());
            userResource.setRoles(source.getRoles());
            userResource.setTrueName(source.getTruename());
            userResource.setPromoted(source.getPromoted());
            userResource.setPromotedSeq(source.getPromotedSeq());
            userResource.setPromotedTime(source.getPromotedTime());
            try {
                userResource.add(linkTo(methodOn(UserController.class).getUserById(userResource.getUserId())).withSelfRel());
                userResource.add(linkTo(methodOn(UserController.class).setUserOrg(userResource.getUserId(), null, null)).withRel("patch_set_user_org"));
                userResource.add(linkTo(methodOn(UserController.class).setUserPost(userResource.getUserId(), null)).withRel("patch_set_user_post"));
                userResource.add(linkTo(methodOn(UserController.class).setUserRoleGroup(userResource.getUserId(), null)).withRel("patch_set_user_role_group"));
                userResource.add(linkTo(methodOn(UserController.class).setLock(userResource.getUserId())).withRel("patch_set_lock"));
                userResource.add(linkTo(methodOn(UserController.class).setUnLock(userResource.getUserId())).withRel("patch_set_unlock"));
                userResource.add(linkTo(methodOn(UserController.class).setPromotedTeacher(userResource.getUserId())).withRel("patch_set_promoted_teacher"));
                userResource.add(linkTo(methodOn(UserController.class).cancelPromotedTeacher(userResource.getUserId())).withRel("patch_cancel_promoted_teacher"));
                userResource.add(linkTo(methodOn(UserController.class).setPromotedSeq(userResource.getUserId(), null)).withRel("patch_set_promoted_seq"));
                userResource.add(linkTo(methodOn(UserController.class).editUserName(null, userResource.getUserId())).withRel("patch_edit_user_name"));
                userResource.add(linkTo(methodOn(UserController.class).editUserPassWord(null, userResource.getUserId())).withRel("patch_edit_user_password"));
                userResource.add(linkTo(methodOn(UserController.class).editUserInfo(null, userResource.getUserId())).withRel("patch_edit_user_info"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userResource;
        });

        PagedResources ok = assembler.toResource(newPage);
        ok.add(linkTo(methodOn(UserController.class).addUser(null, null)).withRel("post_add_user"));
        ok.add(linkTo(methodOn(UserController.class).setUserOrgByIds(null, null)).withRel("patch_set_org"));
        ok.add(linkTo(methodOn(UserController.class).setUserPostByIds(null, null)).withRel("patch_set_post"));
        ok.add(linkTo(methodOn(RoleController.class).getAllRoles()).withRel("get_role_list"));
        return ResponseEntity.ok(ok);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getUserById(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser.getUser() == null) {
            throw new UserNotExistsException();
        }
        Map<Integer, Org> orgMap = orgService.getOrgMap();

        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        String orgNames = Arrays.stream(userResource.getOrgIds().split("\\|"))
                .map(s -> {
                    if (orgMap.containsKey(Integer.valueOf(s))) {
                        return orgMap.get(Integer.valueOf(s)).getName();
                    } else {
                        return "0";
                    }
                })
                .reduce((acc, item) -> String.format("%s|%s", acc, item)).get();
        userResource.setOrgNames(orgNames);
        if (jelUser.getUser().getPostId() == null||jelUser.getUser().getPostId()==0) {
            userResource.setPost("暂无");
        } else {
            Post post = postService.getPost(jelUser.getUser().getPostId());
            userResource.setPost(post.getName());
        }
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")
    })
    public ResponseEntity<?> getUserByCurrent(@AuthenticationPrincipal UserDetails principalUser) throws Exception {
        Integer userId = Integer.parseInt(principalUser.getUsername());
        JelUser jelUser = userService.findById(userId);
        UserResource resource = jelUserResourceAssembler.toResource(jelUser);
        Map<Integer, Org> orgMap = orgService.getOrgMap();
        String orgNames = Arrays.stream(resource.getOrgIds().split("\\|"))
                .map(s -> {
                    if (orgMap.containsKey(Integer.valueOf(s))) {
                        return orgMap.get(Integer.valueOf(s)).getName();
                    } else {
                        return "0";
                    }
                })
                .reduce((acc, item) -> String.format("%s|%s", acc, item)).get();
        resource.setOrgNames(orgNames);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{id}/setRoleGroup", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID设置角色信息", notes = "根据ID设置角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "userSetRoleGroup", value = "设置角色组", required = true, dataType = "UserSetRoleGroup")

    })
    public ResponseEntity<?> setUserRoleGroup(@PathVariable Integer id, @Validated @RequestBody UserSetRoleGroup userSetRoleGroup) throws Exception {
        JelUser jelUser = userService.updateUserByRoleId(userSetRoleGroup, id);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}/lock", method = RequestMethod.PATCH)
    @ApiOperation(value = "封禁用户", notes = "根据ID设置封禁用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> setLock(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser == null) {
            throw new UserNotExistsException();
        }
        userService.lock(id);
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/unlock", method = RequestMethod.PATCH)
    @ApiOperation(value = "解禁用户", notes = "根据ID设置解禁用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> setUnLock(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser == null) {
            throw new UserNotExistsException();
        }
        userService.unlock(id);
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/resetPass", method = RequestMethod.PATCH)
    @ApiOperation(value = "重置密码", notes = "根据ID重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> resetPass(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser == null) {
            throw new UserNotExistsException();
        }
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/setPromotedTeacher", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID设置推荐老师", notes = "根据ID设置推荐老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> setPromotedTeacher(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser.getUser() == null) {
            throw new UserNotExistsException();
        }
        userService.setPromotedTeacher(id, Boolean.TRUE);
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/cancelPromotedTeacher", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID取消推荐老师", notes = "根据ID取消推荐老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> cancelPromotedTeacher(@PathVariable Integer id) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser.getUser() == null) {
            throw new UserNotExistsException();
        }
        userService.setPromotedTeacher(id, Boolean.FALSE);
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/setPromotedSeq", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID设置推荐老师排序号", notes = "根据ID设置推荐老师排序号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "setPromotedSeq", value = "新增用户", required = true, dataType = "SetPromotedSeq")

    })
    public ResponseEntity<?> setPromotedSeq(@PathVariable Integer id, @Validated @RequestBody SetPromotedSeq setPromotedSeq) throws Exception {
        JelUser jelUser = userService.findById(id);
        if (jelUser.getUser() == null) {
            throw new UserNotExistsException();
        }
        userService.setPromotedSeq(id, setPromotedSeq.getSeq());
        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/user/{id}/setPost", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID设置岗位信息", notes = "根据ID设置岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "userSetPost", value = "分配岗位数据", required = true, dataType = "UserSetPost")

    })
    public ResponseEntity<?> setUserPost(@PathVariable Integer id, @Validated @RequestBody UserSetPost userSetPost) throws Exception {
        LOGGER.debug(String.format("UserSetPost.getpostid = %s", userSetPost.getPostid()));
        JelUser jelUser = userService.findById(id);
        if (jelUser == null) {
            throw new UserNotExistsException();
        }
        if (userSetPost.getPostid() != 0) {
            Post post = postService.findByID(userSetPost.getPostid());
            if (post == null) {
                throw new PostNotFoundException();
            }
        }
        JelUser jelUser1 = userService.setPostByPostIdUpdatePostMember(userSetPost.getPostid(), id);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser1);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{id}/setOrg", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据ID设置组织机构信息", notes = "根据ID设置组织机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "userSetOrg", value = "设置机构", required = true, dataType = "UserSetOrg")

    })
    public ResponseEntity<?> setUserOrg(
            @PathVariable Integer id,
            @RequestBody UserSetOrg userSetOrg,
            @AuthenticationPrincipal UserDetails principalUser
    ) throws Exception {
        UserSetOrgResource userSetOrgResource = new UserSetOrgResource();

        JelUser jelUser = userService.updateOrgByUserId(
                id,
                Integer.valueOf(principalUser.getUsername()),
                userSetOrg.getOrgIds()
        );
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/setOrg", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据Ids(批量)设置组织机构信息", notes = "根据Ids(批量)设置组织机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "userSetOrg", value = "设置机构", required = true, dataType = "UserSetOrg")

    })
    public ResponseEntity<?> setUserOrgByIds(
            @RequestBody UserSetOrg userSetOrg,
            @AuthenticationPrincipal UserDetails principalUser
    ) throws Exception {
        userService.updateOrgByUserIds(
                userSetOrg.getIds(),
                Integer.valueOf(principalUser.getUsername()),
                userSetOrg.getOrgIds());
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/user/setPost", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据Ids(批量)设置岗位信息", notes = "根据Ids(批量)设置岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "userSetPostIDs", value = "设置岗位", required = true, dataType = "UserSetPostIDs")

    })
    public ResponseEntity<?> setUserPostByIds(
            @RequestBody UserSetPostIDs userSetPostIDs,
            @AuthenticationPrincipal UserDetails principalUser
    ) throws Exception {

        userService.updatePostByUserIds(userSetPostIDs.getIds(), Integer.valueOf(principalUser.getUsername()), userSetPostIDs.getPostId());

        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/user/count", method = RequestMethod.GET)
    @ApiOperation(value = "根据type获取用户统计数", notes = "根据type获取用户统计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),

    })
    public ResponseEntity<?> getCountByType() throws Exception {

        Integer integer = userService.countForLocked();
        UserCountResource userCountResource = new UserCountResource();
        userCountResource.setLockCount(integer);
        Long all = userService.countByAllUser();
        userCountResource.setAllCount(all);
        return ResponseEntity.ok(userCountResource);
    }

    @RequestMapping(value = "/user/{id}/editName", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据id修改用户名", notes = "根据id修改用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "userSetName", value = "设置岗位", required = true, dataType = "UserSetName")

    })
    public ResponseEntity<?> editUserName(@RequestBody UserSetName userSetName,
                                          @PathVariable Integer id
    ) throws Exception {
        JelUser jelUser = userService.editUserName(userSetName.getName(), id);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

//    @RequestMapping(value = "/user/{id}/editPassWord", method = RequestMethod.POST)
//    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
//=======
//        JelUser jelUser = new JelUser();
//        jelUser.setUser(user);
//        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
//        return new ResponseEntity<>(userResource, HttpStatus.OK);
//    }

    @RequestMapping(value = "/user/{id}/editPassWord", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据id修改用户密码", notes = "根据id修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "userSetPassWord", value = "确认用户密码", required = true, dataType = "UserSetPassWord")

    })
    public ResponseEntity<?> editUserPassWord(@RequestBody UserSetPassWord userSetPassWord,
                                              @PathVariable Integer id
    ) throws Exception {
        if (!userSetPassWord.getQ2password().equals(userSetPassWord.getPassword())) {
            //2次密码不一致
            throw new TwoDifferentPasswordException();
        }
        JelUser jelUser = userService.editUserPwd(passwordEncoder.encode(userSetPassWord.getPassword()), id);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}/editUserInfo", method = RequestMethod.PATCH)
    @ApiOperation(value = "根据id编辑用户信息", notes = "根据id编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "userSetInfo", value = "用户编辑信息", required = true, dataType = "UserSetInfo"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> editUserInfo(@RequestBody UserSetInfo userSetInfo,
                                          @PathVariable Integer id
    ) throws Exception {

        EditUserInfoDTO editUserInfoDTO = userSetInfo.toDTO(source -> {
            EditUserInfoDTO edit = new EditUserInfoDTO();
            edit.setIdcard(source.getIdcard());
            edit.setAbout(source.getAbout());
            edit.setMobile(source.getMobile());
            edit.setQq(source.getQq());
            edit.setSignature(source.getSignature());
            edit.setGender(source.getGender());
            edit.setIam(source.getIam());
            edit.setWeibo(source.getWeibo());
            edit.setSite(source.getSite());
            edit.setWeixin(source.getWeixin());
            edit.setTurename(source.getTurename());
            return edit;
        });
        editUserInfoDTO.setUserId(id);
        JelUser jelUser = userService.updateUserProfile(editUserInfoDTO);
        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }
}
