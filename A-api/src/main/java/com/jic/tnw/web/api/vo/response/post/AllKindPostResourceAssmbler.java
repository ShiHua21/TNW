package com.jic.tnw.web.api.vo.response.post;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AllKindPostResourceAssmbler extends ResourceAssemblerSupport<AllKindPost,AllKindPostResource> {

public AllKindPostResourceAssmbler(Class<?> controllerClass, Class<AllKindPostResource> resourceType) {
        super(controllerClass, resourceType);
        }

    @Override
    public AllKindPostResource toResource(AllKindPost entity) {
    AllKindPostResource allKindPostResource = new AllKindPostResource();
    allKindPostResource.setAllKindPost(entity);
        try {
            if (entity instanceof PostGroupResource){
                PostGroupResource changePostGroupResource = (PostGroupResource)entity;
//            allKindPostResource.add(linkTo(methodOn(PostController.class).deletePostGroup(changePostGroupResource.getPostID())).withSelfRel());
            }
            if (entity instanceof PostResource){
                PostResource changePostResource = (PostResource)entity;
//            allKindPostResource.add(linkTo(methodOn(PostController.class).deletePost(changePostResource.getPostid(), null)).withSelfRel());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return allKindPostResource;
    }
}
