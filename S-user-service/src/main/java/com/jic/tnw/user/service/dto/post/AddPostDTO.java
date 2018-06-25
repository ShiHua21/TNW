package com.jic.tnw.user.service.dto.post;

/**
 * Created by lee5hx on 2018/4/9.
 */
public class AddPostDTO {

    private String groupId;
    private String name;
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
