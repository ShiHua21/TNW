package com.jic.tnw.web.api.vo.response.post;

import org.springframework.hateoas.ResourceSupport;


public class AllKindPostResource extends ResourceSupport {
    private AllKindPost posts;

    public AllKindPost getAllKindPost() {
        return posts;
    }

    public void setAllKindPost(AllKindPost posts) {
        this.posts = posts;
    }
}
