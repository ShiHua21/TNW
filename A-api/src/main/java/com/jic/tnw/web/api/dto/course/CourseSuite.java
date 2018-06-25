package com.jic.tnw.web.api.dto.course;

import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import com.jic.tnw.db.mysql.tables.pojos.Course;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class CourseSuite {
    private Course course;
    private CourseSet courseSet;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseSet getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(CourseSet courseSet) {
        this.courseSet = courseSet;
    }
}
