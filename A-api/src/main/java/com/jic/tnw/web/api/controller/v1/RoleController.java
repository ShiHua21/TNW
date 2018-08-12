package com.jic.tnw.web.api.controller.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.RoleCanNotEditEcxeption;
import com.jic.tnw.common.exception.RoleIDNotExistException;
import com.jic.tnw.db.mysql.tables.pojos.Role;
import com.jic.tnw.user.service.RoleService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.WurTree;
import com.jic.tnw.user.service.dto.role.AddRoleDTO;
import com.jic.tnw.user.service.dto.role.EditRoleDTO;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.web.api.vo.request.role.*;
import com.jic.tnw.web.api.vo.response.role.RoleResource;
import com.jic.tnw.web.api.vo.response.role.RoleResourceAssembler;
import com.jic.tnw.web.api.vo.response.role.WurTreeResource;
import com.jic.tnw.web.api.vo.response.role.WurTreeResourceAssembler;
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
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author gtp
 * @date 2018/3/21
 */
@ApiIgnore
@RestController
@RequestMapping("/v1")
@Api(description = "角色管理", tags = {"F-用户模块-5"})
public class RoleController {
    private static final Log LOGGER = LogFactory.getLog(RoleController.class);
    private final ObjectMapper mapper;
    private final RoleService roleService;
    private final UserService userService;

    private final WurTreeResourceAssembler wurTreeResourceAssembler = new WurTreeResourceAssembler(RoleController.class, WurTreeResource.class);
    private final RoleResourceAssembler roleResourceAssembler = new RoleResourceAssembler(RoleController.class, RoleResource.class);

    @Autowired
    public RoleController(ObjectMapper objectMapper, RoleService roleService, UserService userService) {

        this.mapper = objectMapper;
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ApiOperation(value = "返回角色列表", notes = "返回角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getRoles(String name, String code, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        Map<String, Object> conditionMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(name)) {
            conditionMap.put("name", name);
        }
        if (!StringUtils.isEmpty(code)) {
            conditionMap.put("code", code);
        }
        Page<Role> page = roleService.findWithPageable(pageable, conditionMap);

        Page<RoleResource> newpage = page.map(source -> {

            RoleResource roleResource = new RoleResource();
            roleResource.setCreatTime(source.getCreatedTime());
            roleResource.setCode(source.getCode());
            roleResource.setName(source.getName());
            roleResource.setRoleId(source.getId());
            JelUser jelUser = userService.findById(source.getCreatedUserId().toString());
            roleResource.setCreateName(jelUser.getUser().getLuusername());
            try {
                roleResource.add(linkTo(methodOn(RoleController.class).getUserById(source.getId())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return roleResource;

        });
        PagedResources ok = assembler.toResource(newpage);
        ok.add(linkTo(methodOn(RoleController.class).addRole(null, null)).withRel("post_role_add"));
        ok.add(linkTo(methodOn(RoleController.class).getWurTree()).withRel("get_role_tree"));
        ok.add(linkTo(methodOn(RoleController.class).verifyAddRoleCode(null, null)).withRel("post_add_role_verify_code"));
        ok.add(linkTo(methodOn(RoleController.class).verifyAddRoleName(null, null)).withRel("post_add_role_verify_name"));
        ok.add(linkTo(methodOn(RoleController.class).verifyEditRoleName(null, null)).withRel("post_edit_role_verify_name"));

        return ResponseEntity.ok(ok);
    }

    @ApiIgnore
    @RequestMapping(value = "/roles/all", method = RequestMethod.GET)
    @ApiOperation(value = "返回所有角色列表", notes = "返回所有角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getAllRoles() throws Exception {
        List<Role> list = roleService.findAll();
        List<RoleResource> resources = roleResourceAssembler.toResources(list);
        Resources<RoleResource> wrapped = new Resources<>(
                resources
//                linkTo(methodOn(UserGroupController.class).addUserGroup(null, null)).withRel("post_add_user_group")
        );

        return new ResponseEntity<>(wrapped, HttpStatus.OK);

    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addRole", value = "新增用户", required = true, dataType = "AddRole")

    })
    public ResponseEntity<?> addRole(@Validated @RequestBody AddRole addRole, @AuthenticationPrincipal UserDetails
            user) throws Exception {

        AddRoleDTO addRoleDTO = new AddRoleDTO();
        addRoleDTO.setCode(addRole.getCode());
        addRoleDTO.setName(addRole.getName());
        addRoleDTO.setIds(addRole.getIds());

        Role role = roleService.addRole(addRoleDTO, Integer.valueOf(user.getUsername()));
        List<String> ids = mapper.readValue(role.getData(), List.class);
        Map<String, Integer> map = roleService.getIdByCodeRoleMap(role.getData());
        roleResourceAssembler.setMap(map);
        roleResourceAssembler.setIds(ids);
        RoleResource roleResource = roleResourceAssembler.toResource(role);
        return new ResponseEntity<>(roleResource, HttpStatus.OK);

    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑角色", notes = "根据角色ID编辑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "editRole", value = "编辑角色", required = true, dataType = "EditRole")

    })
    public ResponseEntity<?> editRole(@Validated @RequestBody EditRole editRole, @AuthenticationPrincipal UserDetails
            user, @PathVariable Integer id) throws Exception {
        EditRoleDTO editRoleDTO = new EditRoleDTO();
        editRoleDTO.setIds(editRole.getIds());
        editRoleDTO.setName(editRole.getName());
        Role role = roleService.updateRole(editRoleDTO, Integer.valueOf(user.getUsername()), id);
        List<String> ids = mapper.readValue(role.getData(), List.class);
        Map<String, Integer> map = roleService.getIdByCodeRoleMap(role.getData());
        roleResourceAssembler.setMap(map);
        roleResourceAssembler.setIds(ids);
        RoleResource roleResource = roleResourceAssembler.toResource(role);
        return new ResponseEntity<>(roleResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色", notes = "根据角色ID删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> editRole(@PathVariable Integer id) throws Exception {
        judgeCodeForRoleById(id);

        Role role = roleService.deleteById(id);
        List<String> ids = mapper.readValue(role.getData(), List.class);
        Map<String, Integer> map = roleService.getIdByCodeRoleMap(role.getData());
        roleResourceAssembler.setMap(map);
        roleResourceAssembler.setIds(ids);
        RoleResource roleResource = roleResourceAssembler.toResource(role);
        return new ResponseEntity<>(roleResource, HttpStatus.OK);
//        return ResponseEntity.ok("ok");

    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取角色信息", notes = "根据ID获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getUserById(@PathVariable Integer id) throws Exception {
        Role role = roleService.findById(id);
        if (role == null) {
            throw new RoleIDNotExistException();
        }
        List<String> ids = mapper.readValue(role.getData(), List.class);
        Map<String, Integer> map = roleService.getIdByCodeRoleMap(role.getData());
        roleResourceAssembler.setMap(map);
        roleResourceAssembler.setIds(ids);
        RoleResource roleResource = roleResourceAssembler.toResource(role);
        return new ResponseEntity<>(roleResource, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/role/wur/tree", method = RequestMethod.GET)
    @ApiOperation(value = "获取功能树", notes = "获取功能树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getWurTree() throws Exception {
        List<WurTree> list = new ArrayList<>();
        list.add(roleService.getWurTree());
        List<WurTreeResource> resources = wurTreeResourceAssembler.toResources(list);
        Resources<WurTreeResource> wrapped = new Resources<>(
                resources
        );
        return ResponseEntity.ok(wrapped);
    }

    @RequestMapping(value = "/role/verify/add/code", method = RequestMethod.POST)
    @ApiOperation(value = "验证新增角色编码", notes = "新增角色编码验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addRoleVerifyCode", value = "验证新增角色编码", required = true, dataType = "AddRoleVerifyCode")

    })
    public ResponseEntity<?> verifyAddRoleCode(@RequestBody AddRoleVerifyCode
                                                       addRoleVerifyCode, @AuthenticationPrincipal UserDetails user) throws Exception {
//        VerifyCodePostResource verifyCodePostResource = new VerifyCodePostResource();
//        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
        roleService.verifyAddRoleCode(addRoleVerifyCode.getCode());
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/role/verify/add/name", method = RequestMethod.POST)
    @ApiOperation(value = "验证新增角色名称", notes = "新增角色名称验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addRoleVerifyName", value = "验证新增角色名称", required = true, dataType = "AddRoleVerifyName")

    })
    public ResponseEntity<?> verifyAddRoleName(@RequestBody AddRoleVerifyName
                                                       addRoleVerifyName, @AuthenticationPrincipal UserDetails user) throws Exception {
//        VerifyCodePostResource verifyCodePostResource = new VerifyCodePostResource();
//        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
        roleService.verifyAddRoleName(addRoleVerifyName.getName());
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/role/verify/edit/name", method = RequestMethod.POST)
    @ApiOperation(value = "验证编辑角色名称", notes = "编辑角色名称验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "editRoleVerifyName", value = "验证编辑角色名称", required = true, dataType = "EditRoleVerifyName")

    })
    public ResponseEntity<?> verifyEditRoleName(@RequestBody EditRoleVerifyName
                                                        editRoleVerifyName, @AuthenticationPrincipal UserDetails user) throws Exception {
        roleService.verifyEditRoleName(editRoleVerifyName.getName(),editRoleVerifyName.getRoleId());
        return ResponseEntity.ok("OK");
    }


    private void judgeCodeForRoleById(Integer id) {

        Role role = roleService.findById(id);
        if (role == null) {
            throw new RoleIDNotExistException();
        }
        String admin_code = "ROLE_SUPER_ADMIN";
        String learn_code = "ROLE_USER";
        String anonym_code = "ROLE_ANONYMOUS";
        String teacher_code = "ROLE_TEACHER";
        String train_code = "ROLE_TRAINING_ADMIN";
        String department_code = "ROLE_DEPARTMENT_ADMIN";

        if (role.getCode().equals(admin_code) || role.getCode().equals(learn_code) || role.getCode().equals(anonym_code) || role.getCode().equals(teacher_code) || role.getCode().equals(train_code) || role.getCode().equals(department_code)) {
            throw new RoleCanNotEditEcxeption();
        }
    }

}
