package com.jic.tnw.web.api.vo.request.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lee5hx on 2018/3/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPost {
    @NotBlank(message = "{user.post.groupID.not.blank}")
    private String groupId;
    @NotBlank(message = "{user.post.name.not.blank}")
    private String name;
    @NotBlank(message = "{user.post.code.not.blank}")
    private String code;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
}
