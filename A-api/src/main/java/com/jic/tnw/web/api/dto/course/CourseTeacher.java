package com.jic.tnw.web.api.dto.course;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class CourseTeacher {
    private Integer id;
    private Integer userId;
    private Boolean visible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
