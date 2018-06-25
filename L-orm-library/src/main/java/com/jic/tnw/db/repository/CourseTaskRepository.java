package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.CourseTask;

import java.util.List;

/**
 * @author lee5hx
 * @date 2018/04/18
 */
public interface CourseTaskRepository extends JooqRepository<CourseTask> {
    List<CourseTask> findByCourseId(Integer courseId);
}
