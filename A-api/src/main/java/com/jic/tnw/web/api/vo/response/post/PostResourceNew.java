package com.jic.tnw.web.api.vo.response.post;

import org.springframework.hateoas.ResourceSupport;

public class PostResourceNew  extends ResourceSupport {
    private String name;
    private String code;
    private String postgroupname;
    private Integer seq;
    private Integer groupid;
    private Integer postid;

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
}
