package com.jic.tnw.web.api.vo.request;


/**
 * @author lee5hx
 */
public class AddCourseVO {
    private Integer orgId;
    private String title;
    private String type;
    private String courseLearnMode;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseLearnMode() {
        return courseLearnMode;
    }

    public void setCourseLearnMode(String courseLearnMode) {
        this.courseLearnMode = courseLearnMode;
    }

    @Override
    public String toString() {
        return "AddCourseVO{" +
                "orgId=" + orgId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", courseLearnMode=" + courseLearnMode +
                '}';
    }
}
