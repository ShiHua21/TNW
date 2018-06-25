package com.jic.tnw.web.api.vo.response.user;

import com.jic.tnw.db.mysql.tables.pojos.PostMember;
import org.springframework.hateoas.ResourceSupport;

public class UserSetPostMemberResource extends ResourceSupport {

    PostMember postMember;

    public PostMember getPostMember() {
        return postMember;
    }

    public void setPostMember(PostMember postMember) {
        this.postMember = postMember;
    }
}
