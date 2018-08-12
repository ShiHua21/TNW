package com.jic.tnw.web.api.vo.response.post;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.tnw.web.api.controller.v1.PostController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PostGroupResourceAssmbler extends ResourceAssemblerSupport<PostGroup,PostGroupResourceNew> {
    public PostGroupResourceAssmbler(Class<?> controllerClass, Class<PostGroupResourceNew> resourceType) {
        super(controllerClass, resourceType);
    }
    @Override
    public PostGroupResourceNew toResource(PostGroup entity) {
        PostGroupResourceNew postGroupResourceNew = new PostGroupResourceNew();
        postGroupResourceNew.setName(entity.getName());
        postGroupResourceNew.setPostID(entity.getId());
        postGroupResourceNew.setSeq(entity.getSeq());
        try {
            Link link = linkTo(
                    PostController.class.getMethod("deletePostGroup", Integer.class)
                    , entity.getId()
            ).withSelfRel();
            postGroupResourceNew.add(link);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return postGroupResourceNew;
    }
}
