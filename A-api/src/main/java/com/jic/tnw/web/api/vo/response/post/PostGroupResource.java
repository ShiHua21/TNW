package com.jic.tnw.web.api.vo.response.post;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class PostGroupResource extends ResourceSupport implements AllKindPost {

    private String name;

    private Integer postID;

    private String type;

//    private List<PostResource> epost;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public  PostGroupResource setUp(PostGroup postGroup){
        PostGroupResource postGroupResource = new PostGroupResource();
        postGroupResource.setName(postGroup.getName());
        postGroupResource.setPostID(postGroup.getId());
        postGroupResource.setType("PostGroup");
        return postGroupResource;
    }

//    public List<PostResource> getEpost() {
//        return epost;
//    }
//
//    public void setEpost(List<PostResource> epost) {
//        this.epost = epost;
//    }

//    public PostGroupResourceNew setUp(PostGroup postGroup, List<PostResource> eplist){
//
//        PostGroupResourceNew postGroupResource = new PostGroupResourceNew();
//        postGroupResource.name = postGroup.getName();
//        postGroupResource.postID = postGroup.getId();
//        postGroupResource.type = "PostGroup";
//        postGroupResource.epost = eplist;
//        try {
//            postGroupResource.add(linkTo(methodOn(PostController.class).deletePostGroup(postGroup.getId())).withSelfRel());
//            if (eplist != null){
//                for (PostResource postResource : eplist){
//
//                    postResource.add(linkTo(methodOn(PostController.class).deletePost(postResource.getPostid(), null)).withSelfRel());
//
//                }
//            }
//        }catch (Exception e) {
//                e.printStackTrace();
//
//        }
//
//        return postGroupResource;
//    }
}
