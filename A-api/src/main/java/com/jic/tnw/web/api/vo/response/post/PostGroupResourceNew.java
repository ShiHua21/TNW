package com.jic.tnw.web.api.vo.response.post;

import org.springframework.hateoas.ResourceSupport;

public class PostGroupResourceNew extends ResourceSupport {

    private String name;

    private Integer postID;

    private Integer seq;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
