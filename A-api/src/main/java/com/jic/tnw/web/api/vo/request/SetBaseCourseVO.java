package com.jic.tnw.web.api.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class SetBaseCourseVO {
    @NotBlank(message = "{course.base.title.not.blank}")
    private String title;
    private String subTitle;
    private String[] tags;
    private Integer categoryId;
    private Integer orgId;
    private String status;
//    private String orgCode;
//
//    public String getOrgCode() {
//        return orgCode;
//    }
//
//    public void setOrgCode(String orgCode) {
//        this.orgCode = orgCode;
//    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
