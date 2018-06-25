package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.user.service.PostGroupService;
import com.jic.tnw.user.service.PostService;
import com.jic.tnw.user.service.dto.post.*;
import com.jic.tnw.web.api.vo.request.post.AddPost;
import com.jic.tnw.web.api.vo.request.post.CreatePostGroup;
import com.jic.tnw.web.api.vo.request.post.EditPost;
import com.jic.tnw.web.api.vo.request.post.EditPostGroup;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by lee5hx on 2017/12/10.
 */
@RestController
@RequestMapping("/v1")
@Api(description = "岗位管理", tags = {"F-用户模块-3"})
public class PostController {

    private static final Log LOGGER = LogFactory.getLog(PostController.class);

    private final LocaleMessageSourceService localeMessageSourceService;

    private final ObjectMapper mapper;

    private final PostService postService;

    private final PostGroupService postGroupService;

    private PostGroupResourceAssmbler postGroupResourceAssmbler = new PostGroupResourceAssmbler(PostController.class, PostGroupResourceNew.class);
    private PostResourceAssembler postResourceAssembler = new PostResourceAssembler(PostController.class, PostResourceNew.class);
    private AllKindPostResourceAssmbler allKindPostResourceAssmbler = new AllKindPostResourceAssmbler(PostController.class,AllKindPostResource.class);
    @Autowired
    public PostController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper, PostService postService, PostGroupService postGroupService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.postService = postService;
        this.postGroupService = postGroupService;
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    @ApiOperation(value = "返回岗位信息", notes = "返回机构列表(根据岗位分组)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getPost() throws Exception {
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
                changePostGroupResource.add(linkTo(methodOn(PostController.class).deletePostGroup(changePostGroupResource.getPostID())).withSelfRel());
            }
            if (allKindPost instanceof PostResource){
                PostResource changePostResource = (PostResource)allKindPost;
                changePostResource.add(linkTo(methodOn(PostController.class).deletePost(changePostResource.getPostid(), null)).withSelfRel());
            }
        }
        List<AllKindPostResource>resources = allKindPostResourceAssmbler.toResources(list);
        Resources<AllKindPostResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(PostController.class).addPost(null, null)).withRel("post_add_post"),
                linkTo(methodOn(PostController.class).addPostGroup(null, null)).withRel("post_add_post_group"),
                linkTo(methodOn(PostController.class).sortPost(null)).withRel("post_sort_post"),
                linkTo(methodOn(PostController.class).verifyNamePost(null,null)).withRel("post_verify_post_name"),
                linkTo(methodOn(PostController.class).verifyCodePost(null,null)).withRel("post_verify_post_code")
                );
        getPostListResource.setPosts(list);
        getPostListResource.add(linkTo(methodOn(PostController.class).addPost(null,null)).withRel("post_add_post"));
        getPostListResource.add(linkTo(methodOn(PostController.class).addPostGroup(null,null)).withRel("post_add_post_group"));
        getPostListResource.add(linkTo(methodOn(PostController.class).sortPost(null)).withRel("sort_post"));
        getPostListResource.add(linkTo(methodOn(PostController.class).verifyCodePost(null,null)).withRel("post_verify_code"));
        getPostListResource.add(linkTo(methodOn(PostController.class).verifyNamePost(null,null)).withRel("post_verify_name"));
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ApiOperation(value = "新增岗位信息", notes = "在岗位分组下新增岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addPost", value = "新增岗位分组", required = true, dataType = "AddPost")

    })
    public ResponseEntity<?> addPost(@Validated @RequestBody AddPost addPost, @AuthenticationPrincipal UserDetails
            user) throws Exception {
//        AddPostResource addPostResource = new AddPostResource();

        AddPostDTO addPostDTO = new AddPostDTO();


        addPostDTO.setCode(addPost.getCode());
        addPostDTO.setGroupId(addPost.getGroupId());
        addPostDTO.setName(addPost.getName());

        Post post = postService.addPost(addPostDTO, Integer.parseInt(user.getUsername()));
//        addPostResource.setCode(post.getCode());
//        addPostResource.setName(post.getName());
//        addPostResource.setGroup_id(post.getGroupId());
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑岗位信息", notes = "根据岗位ID编辑岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editPost", value = "编辑岗位分组", required = true, dataType = "EditPost")
    })
    public ResponseEntity<?> editPostGroup(@PathVariable Integer id, @Validated @RequestBody EditPost
            editPost, @AuthenticationPrincipal UserDetails user) throws Exception {
//        EditPostResource editPostResource = new EditPostResource();


        EditPostDTO editPostDTO = new EditPostDTO();
        editPostDTO.setCode(editPost.getCode());
        editPostDTO.setName(editPost.getName());

        Post post = postService.updatePost(id, editPostDTO, Integer.parseInt(user.getUsername()));
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
    }


    @RequestMapping(value = "/post/verify_code", method = RequestMethod.POST)
    @ApiOperation(value = "验证岗位编码", notes = "岗位编码验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "verifyPostCode", value = "验证岗位编码", required = true, dataType = "VerifyPostCode")

    })
    public ResponseEntity<?> verifyCodePost(@RequestBody VerifyPostCode
                                                    verifyPostCode, @AuthenticationPrincipal UserDetails user) throws Exception {
//        VerifyCodePostResource verifyCodePostResource = new VerifyCodePostResource();
        Post post = postService.verifyCodePost(verifyPostCode);
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/verify_name", method = RequestMethod.POST)
    @ApiOperation(value = "验证岗位名称", notes = "岗位名称验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "verifyPostName", value = "验证岗位名称", required = true, dataType = "VerifyPostName")

    })
    public ResponseEntity<?> verifyNamePost(@RequestBody VerifyPostName
                                                    verifyPostName, @AuthenticationPrincipal UserDetails user) throws Exception {
//        VerifyNamePostResource verifyNamePostResource = new VerifyNamePostResource();
        Post post = postService.verifyNamePost(verifyPostName);
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
    }


    @RequestMapping(value = "/post/sort", method = RequestMethod.POST)
    @ApiOperation(value = "排序岗位", notes = "岗位排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),

    })
    public ResponseEntity<?> sortPost(@RequestBody String[] ids) throws Exception {

        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/post/group/sort", method = RequestMethod.POST)
    @ApiOperation(value = "排序岗位分组", notes = "同级岗位分组排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> sortPostGroup(@RequestBody String[] ids) throws Exception {

        return ResponseEntity.ok("ok");
    }


    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取岗位信息", notes = "根据岗位ID获取岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getPostById(@PathVariable Integer id) throws Exception {
//        PostMessageResource postMessageResource = new PostMessageResource();
        Post post = postService.getPost(id);
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
    }


    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除岗位信息", notes = "根据岗位ID删除岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> deletePost(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user) throws
            Exception {
//        DelPostResource delPostResource = new DelPostResource();
        Post post = postService.delPost(id, Integer.parseInt(user.getUsername()));
        PostResourceNew postResourceNew = postResourceAssembler.toResource(post);
        return new ResponseEntity<>(postResourceNew, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/group", method = RequestMethod.POST)
    @ApiOperation(value = "新增岗位分组", notes = "新增岗位分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "createPostGroup", value = "新增岗位分组", required = true, dataType = "CreatePostGroup")
    })
    public ResponseEntity<?> addPostGroup(@Validated @RequestBody CreatePostGroup createPostGroup,
                                          @AuthenticationPrincipal UserDetails user) throws Exception {
//        AddPostGroupResource addPostGroupResource = new AddPostGroupResource();


        AddPostGroupDTO addPostGroupDTO = new AddPostGroupDTO();
        addPostGroupDTO.setName(createPostGroup.getName());

        PostGroup postGroup = postGroupService.addPostGroup(addPostGroupDTO, Integer.parseInt(user.getUsername()));
        PostGroupResourceNew postGroupResourceNew = postGroupResourceAssmbler.toResource(postGroup);
        return new ResponseEntity<>(postGroupResourceNew, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/group/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取岗位分组信息", notes = "根据岗位分组ID获取岗位分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getPostGroup(@PathVariable Integer id) throws Exception {
        PostGroupMessageResource p = new PostGroupMessageResource();
        PostGroup postGroup = postGroupService.getPostGroup(id);
        PostGroupResourceNew postGroupResourceNew = postGroupResourceAssmbler.toResource(postGroup);
        return new ResponseEntity<>(postGroupResourceNew, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/group/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除岗位分组信息", notes = "根据岗位分组ID删除岗位分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> deletePostGroup(@PathVariable Integer id) throws Exception {
        PostGroup postGroup = postGroupService.delPostGroup(id);
        PostGroupResourceNew postGroupResourceNew = postGroupResourceAssmbler.toResource(postGroup);
        return new ResponseEntity<>(postGroupResourceNew, HttpStatus.OK);
    }

    @RequestMapping(value = "/post/group/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑岗位分组信息", notes = "根据岗位分组ID编辑岗位分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "岗位分组ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editPostGroup", value = "编辑岗位分组", required = true, dataType = "EditPostGroup")
    })
    public ResponseEntity<?> editPostGroup(@PathVariable Integer id, @Validated @RequestBody EditPostGroup
            editPostGroup, @AuthenticationPrincipal UserDetails user) throws Exception {
//        EditPostGroupResource editPostGroupResource = new EditPostGroupResource();
        PostGroup postGroup = postGroupService.updateGroup(id, editPostGroup.getName(), Integer.parseInt(user.getUsername()));
        PostGroupResourceNew postGroupResourceNew = postGroupResourceAssmbler.toResource(postGroup);
        return new ResponseEntity<>(postGroupResourceNew, HttpStatus.OK);
    }

}
