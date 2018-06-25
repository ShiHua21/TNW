package com.jic.tnw.web.api.dto.course;

public class AddCourseSubjectDTO {
    /**
     * 难度
     */
    private String difficuty;
    /**
     * 题干
     *
     */
    private String stem;
    /**
     * 分数
     */
    private Float score;
    /**
     *参考答案
     */
    private String answer;
    /**
     * 解析
     */
    private String analysis;
    /**
     *题目类型
     */
    private String type;
    /**
     * 材料父ID
     */
    private Integer parentId;
    /**
     * 从属于
     */
    private String target;
    /**
     * 题目元信息
     */
    private String metas;

    private String userId;
    private String courseId;

    public String getDifficuty() {
        return difficuty;
    }

    public void setDifficuty(String difficuty) {
        this.difficuty = difficuty;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
