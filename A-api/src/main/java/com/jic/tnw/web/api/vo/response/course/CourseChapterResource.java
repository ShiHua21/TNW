package com.jic.tnw.web.api.vo.response.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author lee5hx
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseChapterResource extends ResourceSupport {

    private Integer courseChapterId;
    private Integer courseId;
    private String type;
    private Integer number;
    private Integer seq;
    private String title;
    private LocalDateTime createdTime;
    private Integer copyId;

    public Integer getCourseChapterId() {
        return courseChapterId;
    }

    public void setCourseChapterId(Integer courseChapterId) {
        this.courseChapterId = courseChapterId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCopyId() {
        return copyId;
    }

    public void setCopyId(Integer copyId) {
        this.copyId = copyId;
    }
}
