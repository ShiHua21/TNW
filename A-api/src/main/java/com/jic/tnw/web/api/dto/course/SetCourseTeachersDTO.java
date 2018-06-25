package com.jic.tnw.web.api.dto.course;

import java.util.List;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class SetCourseTeachersDTO {

    private List<SetCourseTeacher> teachers;

    private Integer sid;
    private Integer cid;

    public List<SetCourseTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<SetCourseTeacher> teachers) {
        this.teachers = teachers;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
