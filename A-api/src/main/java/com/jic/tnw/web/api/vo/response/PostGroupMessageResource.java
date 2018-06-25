package com.jic.tnw.web.api.vo.response;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;

public class PostGroupMessageResource {
    PostGroup postGroup;

    public PostGroup getPostGroup() {
        return postGroup;
    }

    public void setPostGroup(PostGroup postGroup) {
        this.postGroup = postGroup;
    }
}

