package com.jic.tnw.user.service.dto;


/**
 * @author lee5hx
 */
public class AddOrgDTO {

    private String parentId;

    private String name;

    private String code;

    private String description;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddOrgDTO (");
        sb.append(name);
        sb.append(", ").append(parentId);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(")");
        return sb.toString();
    }
}
