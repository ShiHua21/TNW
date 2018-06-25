package com.jic.tnw.web.api.vo.response.tag;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.elearning.web.api.controller.v1.TagController;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import com.jic.tnw.web.api.controller.v1.TagController;
import com.jic.tnw.web.api.vo.request.Tag.TagVerifyName;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author tp
 * @date 2018/4/2
 */

public class TagGroupResourceAssmbler extends ResourceAssemblerSupport<TagGroup, TagGroupResource> {

    public TagGroupResourceAssmbler(Class<?> controllerClass, Class<TagGroupResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public TagGroupResource toResource(TagGroup entry) {
        TagGroupResource tagGroupResource = new TagGroupResource();
        tagGroupResource.setCreated_time(entry.getCreatedTime());
        tagGroupResource.setUpdated_time(entry.getUpdatedTime());
        tagGroupResource.setTag_num(entry.getTagNum());
        tagGroupResource.setName(entry.getName());
        tagGroupResource.setScope(entry.getScope());
        tagGroupResource.setTag_group_Id(entry.getId());
        try {
            Link link = linkTo(
                    TagController.class.getMethod("delTagGroup", Integer.class)
                    , entry.getId()
            ).withSelfRel();
            tagGroupResource.add(link);

            link = linkTo(
                    TagController.class.getMethod("verifyEditTagName", TagVerifyName.class, Integer.class)
                    , null
                    , entry.getId()
            ).withRel("post_verify_edit_tag_name");
            tagGroupResource.add(link);

            
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return tagGroupResource;
    }


    public TagGroupResource toResource(TagGroup entry, List<Tag> list) {
        TagGroupResource tagGroupResource = new TagGroupResource();
        tagGroupResource.setCreated_time(entry.getCreatedTime());
        tagGroupResource.setUpdated_time(entry.getUpdatedTime());
        tagGroupResource.setTag_num(entry.getTagNum());
        tagGroupResource.setName(entry.getName());
        tagGroupResource.setScope(entry.getScope());
        tagGroupResource.setTag_group_Id(entry.getId());
        tagGroupResource.setTagList(list);
        try {
            Link link = linkTo(
                    TagController.class.getMethod("delTagGroup", Integer.class)
                    , entry.getId()
            ).withSelfRel();
            tagGroupResource.add(link);
            link = linkTo(
                    TagController.class.getMethod("verifyEditTagName", TagVerifyName.class, Integer.class)
                    , null
                    , entry.getId()
            ).withRel("post_verify_edit_tag_name");
            tagGroupResource.add(link);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return tagGroupResource;
    }
}
