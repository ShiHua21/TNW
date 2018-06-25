package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.tnw.db.repository.impl.JooqTagGroupTagRepository;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.service.TagGroupService;
import com.jic.elearning.web.api.service.TagService;
import com.jic.tnw.user.service.UserGroupService;
import com.jic.elearning.web.api.vo.request.Tag.AddTagGroup;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import com.jic.tnw.web.api.vo.request.Tag.AddTagGroup;
import com.jic.tnw.web.api.vo.request.Tag.TagVerifyName;
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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
@Api(description = "标签管理", tags = {"G-课程模块-6"})
public class TagController {

    private static final Log LOGGER = LogFactory.getLog(TagController.class);
    private final LocaleMessageSourceService localeMessageSourceService;
    private final ObjectMapper mapper;
    private final TagService tagService;
    private final TagGroupService tagGroupService;
    private final JooqTagGroupTagRepository jooqTagGroupTagRepository;
    private TagResourceAssmbler tagResourceAssmbler = new TagResourceAssmbler(TagController.class, TagResource.class);
    private TagGroupResourceAssmbler tagGroupResourceAssmbler = new TagGroupResourceAssmbler(TagController.class, TagGroupResource.class);

    @Autowired
    public TagController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,
            UserGroupService userGroupService, TagService tagService, TagGroupService tagGroupService, JooqTagGroupTagRepository jooqTagGroupTagRepository) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.tagService = tagService;
        this.tagGroupService = tagGroupService;
        this.jooqTagGroupTagRepository = jooqTagGroupTagRepository;
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回标签列表", notes = "返回标签列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query")
    })
    public ResponseEntity<?> getTags(Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        List<Tag> tagList = tagService.findAll();
        Map<Integer,List<TagGroup>> map = new HashMap<>();
        for (Tag tag: tagList){

            map.put(tag.getId(),tagService.findByTagId(tag.getId()));
        }

//        List<TagResource> resources = tagResourceAssmbler.toResources(tagList,map);
        Page<Tag>page = tagService.findWithPageable(pageable);

        Page<TagResource> newPage = page.map(source -> {
             TagResource tagResource = new TagResource();
             tagResource.setName(source.getName());
                    tagResource.setTag_id(source.getId());
                    tagResource.setCreated_time(source.getCreatedTime());
                    tagResource.setName(source.getName());
                    tagResource.setTagGroupList(map.get(source.getId()));
                    if (source.getOrgCode()==null){
                        tagResource.setOrg_code("");
                    }else {
                        tagResource.setOrg_code(source.getOrgCode());
                    }
                    if (source.getOrgId()==null){

                    }else {
                        tagResource.setOrg_id(source.getOrgId());
                    }
                    try {
                        Link link = linkTo(
                                TagController.class.getMethod("delTags", Integer.class)
                                ,source.getId()
                        ).withSelfRel();
                        tagResource.add(link);

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
            return  tagResource;
        });
        PagedResources ok = assembler.toResource(newPage);
        ok.add(linkTo(methodOn(TagController.class).addTag(null)).withRel("post_add_tag"));
        ok.add(linkTo(methodOn(TagController.class).verifyTagName(null)).withRel("post_add_tag_verify_name"));

        return ResponseEntity.ok(ok);
    }

    @RequestMapping(value = "/tag/group", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回标签组列表", notes = "返回标签组列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query")
    })
    public ResponseEntity<?> getTagGroups(Pageable pageable, PagedResourcesAssembler assembler) throws Exception {
        Page<TagGroup>page = tagGroupService.findWithPageable(pageable);
        Page<TagGroupResource> newPage = page.map(source -> {
            TagGroupResource tagGroupResource = new TagGroupResource();
            tagGroupResource.setCreated_time(source.getCreatedTime());
            tagGroupResource.setUpdated_time(source.getUpdatedTime());
            tagGroupResource.setTag_num(source.getTagNum());
            tagGroupResource.setName(source.getName());
            tagGroupResource.setScope(source.getScope());
            tagGroupResource.setTag_group_Id(source.getId());
//            tagGroupResource.setTagList(list);
            try {
                Link link = linkTo(
                        TagController.class.getMethod("delTagGroup", Integer.class)
                        , source.getId()
                ).withSelfRel();
                tagGroupResource.add(link);
                link = linkTo(
                        TagController.class.getMethod("verifyEditTagName", TagVerifyName.class, Integer.class)
                        , null
                        , source.getId()
                ).withRel("post_verify_edit_tag_name");
                tagGroupResource.add(link);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return tagGroupResource;
        });

        PagedResources ok = assembler.toResource(newPage);
        ok.add(linkTo(methodOn(TagController.class).addTagGroup(null)).withRel("post_add_tag"));
        ok.add(linkTo(methodOn(TagController.class).verifyTagGroupName(null)).withRel("post_add_tag_group_verify_name"));
        ok.add(linkTo(methodOn(TagController.class).getTagMatch(null)).withRel("get_tag_match"));
        return ResponseEntity.ok(ok);
        
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "新增标签", notes = "新增标签", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tagVerifyName", value = "新增名称", required = true, dataType = "TagVerifyName"),
    })
    public ResponseEntity<?> addTag(@Validated @RequestBody TagVerifyName tagVerifyName) throws Exception {
        Tag tag = tagService.addTag(tagVerifyName.getName());
        TagResource tagResource = tagResourceAssmbler.toResource(tag);
        return new ResponseEntity<>(tagResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.PUT, produces = "application/hal+json")
    @ApiOperation(value = "编辑标签", notes = "编辑标签", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "tagVerifyName", value = "编辑名称", required = true, dataType = "TagVerifyName"),

    })
    public ResponseEntity<?> editTag(@PathVariable Integer id, @Validated @RequestBody TagVerifyName tagVerifyName) throws Exception {
        Tag tag = tagService.updateTag(tagVerifyName, id);
        TagResource tagResource = tagResourceAssmbler.toResource(tag);
        return new ResponseEntity<>(tagResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.DELETE, produces = "application/hal+json")
    @ApiOperation(value = "删除标签", notes = "删除标签", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> delTags(@PathVariable Integer id) throws Exception {
        Tag tag = tagService.deleteTag(id);
        TagResource tagResource = tagResourceAssmbler.toResource(tag);
        return new ResponseEntity<>(tagResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "新增标签组", notes = "新增标签组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addTagGroup", value = "新增标签组", required = true, dataType = "AddTagGroup"),

    })
    public ResponseEntity<?> addTagGroup(@Validated @RequestBody AddTagGroup addTagGroup) throws Exception {

        TagGroup tagGroup = tagGroupService.addTagGroup(addTagGroup);
        TagGroupResource tagGroupResource = tagGroupResourceAssmbler.toResource(tagGroup);
        return new ResponseEntity<>(tagGroupResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group/{id}", method = RequestMethod.PUT, produces = "application/hal+json")
    @ApiOperation(value = "编辑标签组", notes = "编辑标签组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签组ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "addTagGroup", value = "编辑标签组", required = true, dataType = "AddTagGroup"),

    })
    public ResponseEntity<?> editTagGroup(@Validated @RequestBody AddTagGroup addTagGroup,@PathVariable Integer id) throws Exception {
        TagGroup tagGroup = tagGroupService.update(addTagGroup,id);
        TagGroupResource tagGroupResource = tagGroupResourceAssmbler.toResource(tagGroup);
        return new ResponseEntity<>(tagGroupResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group/{id}", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "获取标签组", notes = "获取标签组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签组ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> getTagGroup(@PathVariable Integer id) throws Exception {
        TagGroup tagGroup = tagGroupService.findById(id);
        List<Integer> tagIdList = jooqTagGroupTagRepository.findByTagGroupId(tagGroup.getId());
        List<Tag> tagNameList = new ArrayList<>();
        for (Integer tagId : tagIdList) {
            Tag tag = tagService.findById(tagId);
            tagNameList.add(tag);
        }
        TagGroupResource tagGroupResource = tagGroupResourceAssmbler.toResource(tagGroup,tagNameList);
        return new ResponseEntity<>(tagGroupResource, HttpStatus.OK);
    }
    @RequestMapping(value = "/tag/group/match", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "匹配标签列表", notes = "匹配标签列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
//            @ApiImplicitParam(name = "query", value = "查询关键字", dataType = "String", paramType = "query"),

    })
    public ResponseEntity<?> getTagMatch( String query) throws Exception {
        List<Tag> list = new ArrayList<>();
        List<Tag> members = tagService.findAll();
        Map<String, Tag> map = members.stream()
                .collect(
                        Collectors.toMap(o -> o.getId().toString() + ":" + o.getName(), Function.identity())
                );
        list.addAll(tagService.searchTagByName(query));
        //过滤已经存在的成员


        List<TagResource> resources = tagResourceAssmbler.toResources(list);
        Resources<TagResource> wrapped = new Resources<>(
                resources
        );

        return new ResponseEntity<>(wrapped,HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group/{id}", method = RequestMethod.DELETE, produces = "application/hal+json")
    @ApiOperation(value = "删除标签组", notes = "删除标签组", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签组ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> delTagGroup(@PathVariable Integer id) throws Exception {
        TagGroup tagGroup = tagGroupService.deleteTagGroup(id);
        TagGroupResource tagGroupResource = tagGroupResourceAssmbler.toResource(tagGroup);
        return new ResponseEntity<>(tagGroupResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/verify/add/name", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "新增验证标签名称", notes = "新增验证标签名称", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tagVerifyName", value = "名称验证", required = true, dataType = "TagVerifyName")

    })
    public ResponseEntity<?> verifyTagName(@Validated @RequestBody TagVerifyName tagVerifyName) throws Exception {
        tagService.verifyAddTagName(tagVerifyName.getName());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/verify/add/name/{id}", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "编辑验证标签名称", notes = "编辑验证标签名称", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tagVerifyName", value = "名称验证", required = true, dataType = "TagVerifyName"),
            @ApiImplicitParam(name = "id", value = "标签ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> verifyEditTagName(@Validated @RequestBody TagVerifyName tagVerifyName, @PathVariable Integer id) throws Exception {
        tagService.verifyEditName(tagVerifyName.getName(), id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group/verify/add/name", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "新增验证标签组名称", notes = "新增验证标签组名称", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tagVerifyName", value = "名称验证", required = true, dataType = "TagVerifyName")

    })
    public ResponseEntity<?> verifyTagGroupName(@Validated @RequestBody TagVerifyName tagVerifyName) throws Exception {
        tagGroupService.verifyAddTagGroupName(tagVerifyName.getName());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/tag/group/verify/add/name/{id}", method = RequestMethod.POST, produces = "application/hal+json")
    @ApiOperation(value = "编辑验证标签组名称", notes = "编辑验证标签组名称", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tagVerifyName", value = "名称验证", required = true, dataType = "TagVerifyName"),
            @ApiImplicitParam(name = "id", value = "标签组ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> verifyEditTagGroupName(@Validated @RequestBody TagVerifyName tagVerifyName, @PathVariable Integer id) throws Exception {
        tagGroupService.verifyEditTagGroupName(tagVerifyName.getName(), id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
