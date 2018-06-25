package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author lee5hx
 * @date 2018/04/08
 */
public interface CourseSetRepository extends JooqRepository<CourseSet> {
    Page<CourseSet> find(Map<String, Object> conditionMap, Pageable pageable);

//    /**
//     * 详细信息更细
//     * @param entry
//     * @return
//     */
//    CourseSet updateByDetail(CourseSet entry);
//
//    /**
//     * 学习设置更新
//     * @param entry
//     * @return
//     */
//    CourseSet updateBySet(CourseSet entry);
//
//    /**
//     * 课程管理发布课程
//     * @param entry
//     * @return
//     */
//    CourseSet updateByPublished(CourseSet entry);

}
