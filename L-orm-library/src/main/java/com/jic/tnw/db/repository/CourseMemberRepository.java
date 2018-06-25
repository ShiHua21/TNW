package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.CourseMember;

import java.util.List;

/**
 * @author lee5hx
 * @date 2018/04/08
 */
public interface CourseMemberRepository extends JooqRepository<CourseMember> {

    /**
     * 根据课程ID,查询老师
     * SELECT * FROM course_member WHERE courseId = '35' AND role = 'teacher' ORDER BY seq, createdTime DESC
     * @param courseId
     * @return
     */
    List<CourseMember> findTeachersByCourseId(Integer courseId);

}
