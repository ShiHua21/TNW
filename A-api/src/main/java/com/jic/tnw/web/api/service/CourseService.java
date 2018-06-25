package com.jic.tnw.web.api.service;


import com.jic.tnw.db.mysql.tables.pojos.CourseChapter;
import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import com.jic.tnw.db.mysql.tables.pojos.CourseTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author lee5hx
 */
public interface CourseService {
    /**
     * 新增课程
     *
     * @param addCourseDTO
     * @return
     */
    CourseSuite addCourse(AddCourseDTO addCourseDTO);

    /**
     * 返回管理课程列表(分页)
     *
     * @param pageable
     * @param conditionMap
     * @return
     */
    Page<CourseSet> findCourseSetsWithPageable(Pageable pageable, Map<String, Object> conditionMap);

    /**
     * 新增 章 or 节
     *
     * @param addCourseChapter
     * @return
     */
    CourseChapter addCourseChapter(AddCourseChapterDTO addCourseChapter);

    /**
     * 新增任务
     *
     * @param addCourseTask
     * @return
     */
    CourseTask addCourseTask(AddCourseTaskDTO addCourseTask);

    /**
     * 设置基本信息
     *
     * @param setBaseCourseDTO
     * @return
     */
    CourseSuite setBaseCource(SetBaseCourseDTO setBaseCourseDTO);

    /**
     * 设置详细信息
     */
    CourseSuite setDetailCourse(SetDetailCourseDTO setDetailCourseDTO);

    /**
     * 学习设置
     */
    CourseSuite settingCourse(CourseSetDTO courseSetDTO);

    /**
     * 章节 + 任务  List
     *
     * @param sid
     * @param cid
     * @return
     */
    List<CtItem> getCourseCtItems(Integer sid, Integer cid);

    /**
     * 章节 + 任务 sort
     *
     * @param sortCourseTask
     */
    void sortCourseTask(SortCourseTaskDTO sortCourseTask);

    /**
     * 删除章／节
     *
     * @param chapterId
     * @return
     */
    CourseChapter deleteCourseChapter(Integer chapterId);

    /**
     * @param chapterId
     * @param title
     * @return
     */
    CourseChapter updateCourseChapter(Integer chapterId, String title);

    /**
     * @param chapterId
     * @return
     */
    CourseChapter getCourseChapter(Integer chapterId);

    /**
     * @param courseId
     * @return
     */
    CourseSuite setCoursePublish(Integer courseId);

    /**
     * 设置课程讲师
     *
     * @param setCourseTeachers
     * @return
     */
    List<CourseTeacher> setCourseTeachers(SetCourseTeachersDTO setCourseTeachers);

    /**
     * getCourseSuiteById
     * @param courseId
     * @return
     */
    CourseSuite getCourseSuiteById(Integer courseId);


}
