package com.jic.tnw.web.api.vo.response.tag;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.elearning.web.api.controller.v1.TagController;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import com.jic.tnw.web.api.controller.v1.TagController;
import com.jic.tnw.web.api.vo.request.Tag.TagVerifyName;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author tp
 * @date 2018/4/2
 */
public class TagResourceAssmbler extends ResourceAssemblerSupport<Tag, TagResource> {

    public TagResourceAssmbler(Class<?> controllerClass, Class<TagResource> resourceType) {
        super(controllerClass, resourceType);
    }


    public List<TagResource> toResources(Iterable<? extends Tag> entities, Map<Integer,List<TagGroup>> map) {
        List<TagResource> resources = new ArrayList<>();
        List<Tag> tagList = (List<Tag>) entities;
        for (Tag tag:tagList){
            resources.add(toResource(tag,map.get(tag.getId())));
        }
        return resources;
    }


    public TagResource toResource(Tag entity,List<TagGroup> list) {
        TagResource tagResource = new TagResource();
        tagResource.setTag_id(entity.getId());
        tagResource.setCreated_time(entity.getCreatedTime());
        tagResource.setName(entity.getName());
        tagResource.setTagGroupList(list);
        if (entity.getOrgCode()==null){
            tagResource.setOrg_code("");
        }else {
            tagResource.setOrg_code(entity.getOrgCode());
        }
        if (entity.getOrgId()==null){

        }else {
            tagResource.setOrg_id(entity.getOrgId());
        }
        try {
            Link link = linkTo(
                    TagController.class.getMethod("delTags", Integer.class)
                    ,entity.getId()
            ).withSelfRel();
            tagResource.add(link);
//            link  = linkTo(
//                    TagController.class.getMethod("verifyEditTagName",TagVerifyName.class,Integer.class)
//                    ,null
//                    ,entity.getId()
//            ).withRel("post_verify_edit_tag_name");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return tagResource;
    }




    @Override
    public TagResource toResource(Tag entity) {
        TagResource tagResource = new TagResource();
        tagResource.setTag_id(entity.getId());
        tagResource.setCreated_time(entity.getCreatedTime());
        tagResource.setName(entity.getName());
        if (entity.getOrgCode()==null){
            tagResource.setOrg_code("");
        }else {
            tagResource.setOrg_code(entity.getOrgCode());
        }
        if (entity.getOrgId()==null){

        }else {
            tagResource.setOrg_id(entity.getOrgId());
        }
        try {
            Link link = linkTo(
                    TagController.class.getMethod("delTags", Integer.class)
                    ,entity.getId()
            ).withSelfRel();
            tagResource.add(link);
            link  = linkTo(
                    TagController.class.getMethod("verifyEditTagName",TagVerifyName.class,Integer.class)
                    ,null
                    ,entity.getId()
            ).withRel("post_verify_edit_tag_name");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return tagResource;
    }
}
