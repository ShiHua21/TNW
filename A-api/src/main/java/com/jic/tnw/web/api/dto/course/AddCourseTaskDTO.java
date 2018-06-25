package com.jic.tnw.web.api.dto.course;

import io.swagger.models.auth.In;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class AddCourseTaskDTO {
    private String mediaType;
    private String finishDetail;
    private Integer fromCourseId;
    private Integer fromCourseSetId;
    private String title;
    private String content;
    private Integer createUid;
    private Boolean optional;

    public Integer getFromCourseSetId() {
        return fromCourseSetId;
    }

    public void setFromCourseSetId(Integer fromCourseSetId) {
        this.fromCourseSetId = fromCourseSetId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFinishDetail() {
        return finishDetail;
    }

    public void setFinishDetail(String finishDetail) {
        this.finishDetail = finishDetail;
    }

    public Integer getFromCourseId() {
        return fromCourseId;
    }

    public void setFromCourseId(Integer fromCourseId) {
        this.fromCourseId = fromCourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
