package com.jic.tnw.web.api.vo.response.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author lee5hx
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseResource extends ResourceSupport {

    private Integer courseSetId;
    private Integer courseId;
    private String cover;
    private String title;
    private Integer categoryId;
    private String categoryName;
    private String orgCode;
    private Integer orgId;
    private String orgName;
    private String serializeMode;
    private Integer studentNum;
    private String status;
    private Integer creator;
    private String creatorName;
    private String subTitle;
    private String tags;
    private String summary;
    private String goals;
    private String audiences;
    private Boolean enableFinish;
    private Boolean buyAble;

    public Boolean getBuyAble() {
        return buyAble;
    }

    public void setBuyAble(Boolean buyAble) {
        this.buyAble = buyAble;
    }

    public Boolean getEnableFinish() {
        return enableFinish;
    }

    public void setEnableFinish(Boolean enableFinish) {
        this.enableFinish = enableFinish;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getAudiences() {
        return audiences;
    }

    public void setAudiences(String audiences) {
        this.audiences = audiences;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCourseSetId() {
        return courseSetId;
    }

    public void setCourseSetId(Integer courseSetId) {
        this.courseSetId = courseSetId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSerializeMode() {
        return serializeMode;
    }

    public void setSerializeMode(String serializeMode) {
        this.serializeMode = serializeMode;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
