package com.jic.tnw.web.api.vo.request;

import org.hibernate.validator.constraints.NotBlank;

public class EditCourseChapterVO {
    @NotBlank(message = "{course.chapter.edit.title.not.blank}")

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
