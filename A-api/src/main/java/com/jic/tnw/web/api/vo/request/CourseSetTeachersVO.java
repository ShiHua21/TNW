package com.jic.tnw.web.api.vo.request;

import com.jic.elearning.web.api.dto.course.SetCourseTeacher;

import java.util.List;

public class CourseSetTeachersVO {
    private List<SetCourseTeacher> teachers;


    public List<SetCourseTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<SetCourseTeacher> teachers) {
        this.teachers = teachers;
    }
}
