package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Course;

/**
 * @author lee5hx
 * @date 2018/04/08
 */
public interface CourseRepository extends JooqRepository<Course> {
    /**
     * 更新课程详细信息
     * @param entry
     * @return
     */
    Course updateByDetail(Course entry);

    /**
     * 学习设置更新课程
     * @param entry
     * @return
     */
    Course updateBySet(Course entry);

    Course updateByPublished(Course entry);

}
