package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.CourseChapter;

import java.util.List;

/**
 * @author lee5hx
 * @date 2018/04/08
 */
public interface CourseChapterRepository extends JooqRepository<CourseChapter> {

    List<CourseChapter> findByCourseId(Integer courseId);

}
