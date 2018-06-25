package com.jic.tnw.web.api.vo.response.category;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * @author lee5hx
 */
public class CategoryGroupResource extends ResourceSupport {

    private Integer categoryGroupId;
    private String  code;
    private String  name;
    private Integer depth;

    public Integer getCategoryGroupId() {
        return categoryGroupId;
    }

    public void setCategoryGroupId(Integer categoryGroupId) {
        this.categoryGroupId = categoryGroupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
