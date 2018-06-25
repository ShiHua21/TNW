package com.jic.tnw.web.api.vo.response.course;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author lee5hx
 */
public class CourseSetResource extends ResourceSupport {

    private Integer courseSetId;

    public Integer getCourseSetId() {
        return courseSetId;
    }

    public void setCourseSetId(Integer courseSetId) {
        this.courseSetId = courseSetId;
    }
}
