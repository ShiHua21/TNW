package com.jic.tnw.web.api.vo.response.post;

import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.web.api.controller.v1.PostController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PostResourceAssembler extends ResourceAssemblerSupport <Post,PostResourceNew> {
    public PostResourceAssembler(Class<?> controllerClass, Class<PostResourceNew> resourceType) {
        super(controllerClass, resourceType);
    }
    @Override
    public PostResourceNew toResource(Post entity) {
        PostResourceNew postResourceNew = new PostResourceNew();
        postResourceNew.setPostid(entity.getId());
        postResourceNew.setName(entity.getName());
        postResourceNew.setCode(entity.getCode());
        postResourceNew.setGroupid(entity.getGroupId());
        postResourceNew.setSeq(entity.getSeq());
        try {
            Link link = linkTo(
                    PostController.class.getMethod("deletePost", Integer.class, UserDetails.class)
                    , entity.getId()
            ).withSelfRel();
            postResourceNew.add(link);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return postResourceNew;
    }
}
