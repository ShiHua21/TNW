package com.jic.tnw.web.api.vo.response.org;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lee5hx on 2017/10/17.
 */
public class AddOrgResource extends ResourceSupport {

    private Integer parentId;

    private String name;

    private String code;

    private String description;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
