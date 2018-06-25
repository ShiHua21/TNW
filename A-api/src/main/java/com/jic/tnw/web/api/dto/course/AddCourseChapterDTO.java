package com.jic.tnw.web.api.dto.course;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class AddCourseChapterDTO {
    private Integer courseId;
    private String title;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AddCourseChapterDTO{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
