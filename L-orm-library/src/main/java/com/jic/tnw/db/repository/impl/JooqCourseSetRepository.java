package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import com.jic.tnw.db.mysql.tables.records.CourseSetRecord;
import com.jic.tnw.db.repository.CourseSetRepository;
import com.jic.tnw.db.repository.utils.JOOQPageSortUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author lee5hx
 * @date 2018/04/08
 */
@Repository
public class JooqCourseSetRepository extends AbstractJooqRepository<CourseSet,CourseSetRecord> implements CourseSetRepository {


    private static final Log LOGGER = LogFactory.getLog(JooqCourseSetRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqCourseSetRepository(DSLContext jooq) {
        this.jooq = jooq;
    }


    @Override
    public Page<CourseSet> find(Map<String, Object> conditionMap, Pageable pageable) {
        LOGGER.info(String.format("Finding [%d] user entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));

        List<CourseSetRecord> queryResults = jooq.select(
        ).from(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET)
                .where(createWhereConditions(conditionMap))
                .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET, pageable.getSort()))
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetchInto(CourseSetRecord.class);

        List<CourseSet> userEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] user entries for page: [%d]",
                userEntries.size(),
                pageable.getPageNumber())
        );

        long totalCount = findCountByConditionMap(conditionMap);
        PageImpl<CourseSet> page = new PageImpl<>(userEntries, pageable, totalCount);

        return page;
    }


    private long findCountByConditionMap(Map<String, Object> conditionMap) {
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET)
                        .where(createWhereConditions(conditionMap))
        );
        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }

    private Condition createWhereConditions(Map<String, Object> conditionMap) {
        Condition condition = com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.ID.isNotNull();
        if (conditionMap.get("org") != null) {
            String org = String.valueOf(conditionMap.get("org"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.ORG_CODE.like("%"+String.format("%s.",org)+"%"));
        }
        if (conditionMap.get("category") != null) {
            String categoryId = String.valueOf(conditionMap.get("category"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.CATEGORY_ID.eq(Integer.valueOf(categoryId)));
        }
        if (conditionMap.get("status") != null) {
            String status = String.valueOf(conditionMap.get("status"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.STATUS.eq(status));
        }
        if (conditionMap.get("title") != null) {
            String title = String.valueOf(conditionMap.get("title"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.TITLE.like("%"+title+"%"));
        }
        if (conditionMap.get("creator") != null) {
            String creator = String.valueOf(conditionMap.get("creator"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.CREATOR.eq(Integer.valueOf(creator)));
        }

        return condition;
    }

    @Override
    public CourseSet add(CourseSet entry) {
        CourseSetRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        CourseSet returned = convertQueryResultToPojo(persisted);
        return returned;
    }
    private CourseSetRecord createRecord(CourseSet entry) {

        //INSERT INTO course_set_v8 (orgCode, title, type, orgId, status, creator, createdTime, updatedTime)
        //VALUES ('1.', 'RDS-GELOG', 'normal', '1', 'draft', '3', '1523169327', '1523169327')
//        CourseSetRecord record = new CourseSetRecord();
//        record.setTitle(entry.getTitle());
//        record.setType(entry.getType());
//        record.setOrgId(entry.getOrgId());
//        record.setOrgCode(entry.getOrgCode());
//        record.setStatus(entry.getStatus());
//        record.setCreator(entry.getCreator());
//        record.setCreatedTime(entry.getCreatedTime());
//        record.setUpdatedTime(entry.getUpdatedTime());


        return jooq.newRecord(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET, entry);
    }

    @Override
    public CourseSet delete(Integer id) {
        return null;
    }

    @Override
    public List<CourseSet> findAll() {
        return null;
    }

    @Override
    public CourseSet findById(Integer id) {
        CourseSetRecord courseSetRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET)
                .where(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.ID.eq(id))
                .fetchOne();
        CourseSet courseSet = convertQueryResultToPojo(courseSetRecord);
        return courseSet;
    }

    @Override
    public CourseSet update(CourseSet entry) {
        // UPDATE course_set_v8 SET title = '10点56分', subtitle = '111', tags = '|3|', categoryId = '17', orgCode = '1.4.', serializeMode = 'none', orgId = '4', updatedTime = '1523600556' WHERE id = '34'
        CourseSetRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET, entry);
        resetChangedOnNotNull(record);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET)
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("course set update count = %d",updateCount));
        return findById(entry.getId());
    }

    @Override
    protected Table<CourseSetRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.CourseSet.COURSE_SET;
    }

    @Override
    protected Class<CourseSet> getPojoClass() {
        return CourseSet.class;
    }
}
