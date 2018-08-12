package com.jic.tnw.web.api.vo.response;

import com.jic.tnw.web.api.vo.response.post.AllKindPost;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/17.
 */
public class GetPostListResource extends ResourceSupport {

    private List<AllKindPost> posts;

    public List<AllKindPost> getPosts() {
        return posts;
    }

    public void setPosts(List<AllKindPost> posts) {
        this.posts = posts;
    }
}
