package com.jic.tnw.web.api.vo.request;


import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author lee5hx
 */
@Validated
public class AddCourseChapterVO {

    @NotBlank(message = "{course.chapter.title.name.not.blank}")
    private String title;

    @NotNull(message = "{course.chapter.seq.not.blank}")
    private Integer seq;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "AddCourseChapterVO{" +
                "title='" + title + '\'' +
                ", seq=" + seq +
                '}';
    }
}
