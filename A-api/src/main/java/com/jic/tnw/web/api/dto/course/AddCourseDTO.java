package com.jic.tnw.web.api.dto.course;

import com.jic.tnw.db.mysql.enums.CourseLearnMode;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class AddCourseDTO {
    private Integer orgId;
    private String title;
    private String type;
    private CourseLearnMode courseLearnMode;
    private Integer createUid;

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

    public CourseLearnMode getCourseLearnMode() {
        return courseLearnMode;
    }

    public void setCourseLearnMode(CourseLearnMode courseLearnMode) {
        this.courseLearnMode = courseLearnMode;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    @Override
    public String toString() {
        return "AddCourseDTO{" +
                "orgId=" + orgId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", courseLearnMode=" + courseLearnMode +
                ", createUid=" + createUid +
                '}';
    }
}
