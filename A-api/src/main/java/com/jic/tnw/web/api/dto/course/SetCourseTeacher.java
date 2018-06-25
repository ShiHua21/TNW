package com.jic.tnw.web.api.dto.course;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class SetCourseTeacher {
    private String id;
    private Boolean visible;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
