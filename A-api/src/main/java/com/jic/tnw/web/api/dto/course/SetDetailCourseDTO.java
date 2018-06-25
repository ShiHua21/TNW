package com.jic.tnw.web.api.dto.course;

public class SetDetailCourseDTO {
    private String summary;
    private String[] goals;
    private String[] audiences;
    private Integer courseId;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getGoals() {
        return goals;
    }

    public void setGoals(String[] goals) {
        this.goals = goals;
    }

    public String[] getAudiences() {
        return audiences;
    }

    public void setAudiences(String[] audiences) {
        this.audiences = audiences;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
