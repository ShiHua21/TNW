package com.jic.tnw.web.api.vo.response.post;

import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class PostResource extends ResourceSupport implements AllKindPost {
    private String name;
    private String code;
    private String postgroupname;
    private Integer seq;
    private Integer groupid;
    private Integer postid;
    private String type;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPostgroupname() {
        return postgroupname;
    }

    public void setPostgroupname(String postgroupname) {
        this.postgroupname = postgroupname;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public List<PostResource> setup(List<Post> plist, PostGroup postGroup) {
        if (plist == null){
            return new ArrayList<>();
        }
        List<PostResource> list = new ArrayList<>();
        for (Post post : plist) {
            PostResource postResource = new PostResource();
            postResource.setCode(post.getCode());
            postResource.setName(post.getName());
            postResource.setGroupid(post.getGroupId());
            postResource.setPostgroupname(postGroup.getName());
            postResource.setSeq(post.getSeq());
            postResource.setPostid(post.getId());
            postResource.setType("Post");
            list.add(postResource);
        }
        return list;
    }
}
