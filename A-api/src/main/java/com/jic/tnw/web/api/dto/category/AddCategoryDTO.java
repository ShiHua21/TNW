package com.jic.tnw.web.api.dto.category;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lee5hx on 2018/4/3.
 */
public class AddCategoryDTO {

    private Integer parentId;

    private Integer groupId;

    private String name;

    private String code;

    private String description;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
