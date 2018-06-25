package com.jic.tnw.web.api.vo.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * @author lee5hx
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryResource extends ResourceSupport {

    private Integer categoryId;
    private String  ctierCode;
    private String  code;
    private String  name;
    private String  icon;
    private String  path;
    private Integer weight;
    private Integer groupId;
    private Integer parentId;
    private Integer orgId;
    private String  orgCode;
    private String  description;
    private List<CategoryResource> child;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryResource> getChild() {
        return child;
    }

    public void setChild(List<CategoryResource> child) {
        this.child = child;
    }

    public String getCtierCode() {
        return ctierCode;
    }

    public void setCtierCode(String ctierCode) {
        this.ctierCode = ctierCode;
    }
}
