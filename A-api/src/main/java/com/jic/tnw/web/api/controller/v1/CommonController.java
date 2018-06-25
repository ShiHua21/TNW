package com.jic.tnw.web.api.controller.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.elearning.user.service.*;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.user.service.*;
import com.jic.tnw.user.service.dto.OrgTree;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.service.CategoryService;
import com.jic.elearning.web.api.vo.response.GetPostListResource;
import com.jic.elearning.web.api.vo.response.category.CategoryGroupResource;
import com.jic.elearning.web.api.vo.response.category.CategoryGroupResourceAssembler;
import com.jic.elearning.web.api.vo.response.category.CategoryResource;
import com.jic.elearning.web.api.vo.response.category.CategoryResourceAssembler;
import com.jic.elearning.web.api.vo.response.org.GetOrgTreeResourceAssembler;
import com.jic.elearning.web.api.vo.response.org.OrgTreeResource;
import com.jic.elearning.web.api.vo.response.role.RoleResource;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1")
@Api(description = "公共模块", tags = {"C-公共模块-1"})
public class CommonController {
    private static final Log LOGGER = LogFactory.getLog(CommonController.class);
    private final LocaleMessageSourceService localeMessageSourceService;
    private final ObjectMapper mapper;
    private final OrgService orgService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final PostGroupService postGroupService;
    private final RoleService roleService;
    private final UserService userService;
    private CategoryResourceAssembler categoryResourceAssembler = new CategoryResourceAssembler(CategoryController.class, CategoryResource.class);
    private CategoryGroupResourceAssembler categoryGroupResourceAssembler = new CategoryGroupResourceAssembler(CategoryController.class, CategoryGroupResource.class);
    private AllKindPostResourceAssmbler allKindPostResourceAssmbler = new AllKindPostResourceAssmbler(PostController.class,AllKindPostResource.class);


    @Autowired
    public CommonController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,OrgService orgService,CategoryService categoryService,PostService postService, PostGroupService postGroupService,
            RoleService roleService,UserService userService
           ) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.orgService = orgService;
        this.categoryService = categoryService;
        this.postService = postService;
        this.postGroupService = postGroupService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value = "/common/org/tree", method = RequestMethod.GET,produces="application/hal+json")
    @ApiOperation(value = "返回机构", notes = "返回机构(tree)",produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getCommonOrgTree() throws Exception {
        GetOrgTreeResourceAssembler orgTreeResourceAssembler = new GetOrgTreeResourceAssembler(OrgController.class,OrgTreeResource.class);
        List<OrgTree> list = new ArrayList<>();
        list.add(orgService.getOrgTree());
        List<OrgTreeResource> resources = orgTreeResourceAssembler.toResources(list);
        Resources<OrgTreeResource> wrapped = new Resources<>(
                resources
//                linkTo(methodOn(OrgController.class).addOrg(null,null)).withRel("org_add"),
//                linkTo(methodOn(OrgController.class).sortOrg()).withRel("org_sort")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }
    @RequestMapping(value = "/common/categoryGroups", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回分类分组列表", notes = "返回分类分组列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")
    })
    public ResponseEntity<?> getCommonCategoryGroups() throws Exception {
        List<CategoryGroup> list = categoryService.findCategoryGroups();
        List<CategoryGroupResource> resources = categoryGroupResourceAssembler.toResources(list);
        Resources<CategoryGroupResource> wrapped = new Resources<>(
                resources
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }


    @RequestMapping(value = "/common/categories/{groupId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回分类列表", notes = "返回分类列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getCommonCategories(@PathVariable Integer groupId) throws Exception {
        List<Category> categories = categoryService.findCategoryListByGroupId(groupId);
        List<CategoryResource> resources = categoryResourceAssembler.toResources(categories);
        Resources<CategoryResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(CategoryController.class).addCategory(null, null)).withRel("post_category_add")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }

    @RequestMapping(value = "/common/post", method = RequestMethod.GET)
    @ApiOperation(value = "返回岗位信息", notes = "返回机构列表(根据岗位分组)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getCommonPost() throws Exception {
        GetPostListResource getPostListResource = new GetPostListResource();
//        AllKindPostResource allKindPostResource = new AllKindPostResource();
        List<AllKindPost> list = new ArrayList<>();

        List<Post> allPostList = postService.findAll();
        List<PostGroup> allPostGroupList = postGroupService.findAll();

        for (PostGroup postGroup : allPostGroupList) {
            PostGroupResource postGroupResource = new PostGroupResource();
            list.add(postGroupResource.setUp(postGroup));
            PostResource postResource = new PostResource();
            List<Post>groupIDForPostList = postService.findByGroupID(postGroup.getId(),allPostList);
            List<PostResource>postResourcesList = postResource.setup(groupIDForPostList,postGroup);
            list.addAll(postResourcesList);
        }
        for (AllKindPost allKindPost : list){
            if (allKindPost instanceof PostGroupResource ){
                PostGroupResource changePostGroupResource = (PostGroupResource)allKindPost;
//                changePostGroupResource.add(linkTo(methodOn(PostController.class).deletePostGroup(changePostGroupResource.getPostID())).withSelfRel());
            }
            if (allKindPost instanceof PostResource){
                PostResource changePostResource = (PostResource)allKindPost;
//                changePostResource.add(linkTo(methodOn(PostController.class).deletePost(changePostResource.getPostid(), null)).withSelfRel());
            }
        }
        List<AllKindPostResource>resources = allKindPostResourceAssmbler.toResources(list);
        Resources<AllKindPostResource> wrapped = new Resources<>(
                resources
      );
        getPostListResource.setPosts(list);
       return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }
    @RequestMapping(value = "/common/roles", method = RequestMethod.GET)
    @ApiOperation(value = "返回角色列表", notes = "返回角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getCommonRoles(String name, String code, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
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
            JelUser jelUser = userService.findById(source.getCreatedUserId());
            roleResource.setCreateName(jelUser.getUser().getUsername());

            return roleResource;

        });
        PagedResources ok = assembler.toResource(newpage);

        return ResponseEntity.ok(ok);
    }

}
