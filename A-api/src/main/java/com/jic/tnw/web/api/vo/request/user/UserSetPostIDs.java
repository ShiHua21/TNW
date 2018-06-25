package com.jic.tnw.web.api.vo.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.validation.annotation.Validated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class UserSetPostIDs {

    private String[] ids;

    private Integer postId;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}





