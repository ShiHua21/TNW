package com.jic.tnw.web.api.vo.request;

public class SetDetailCourseVO {

    private String summary;
    private String[] goals;
    private String[] audiences;

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
}
