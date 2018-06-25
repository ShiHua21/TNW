package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.CourseTask;
import com.jic.tnw.db.mysql.tables.records.CourseTaskRecord;
import com.jic.tnw.db.repository.CourseTaskRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author lee5hx
 * @date 2018/04/08
 */
@Repository
public class JooqCourseTaskRepository extends AbstractJooqRepository<CourseTask, CourseTaskRecord> implements CourseTaskRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqCourseTaskRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqCourseTaskRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public CourseTask add(CourseTask entry) {
        CourseTaskRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK)
                .set(createRecord(jooq, entry))
                .returning()
                .fetchOne();
        CourseTask returned = convertQueryResultToPojo(persisted);
        return returned;
    }


    @Override
    public CourseTask delete(Integer id) {
        return null;
    }

    @Override
    public List<CourseTask> findAll() {
        return null;
    }

    @Override
    public CourseTask findById(Integer id) {
        CourseTaskRecord record = jooq.selectFrom(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK)
                .where(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK.ID.eq(id)).fetchOne();
        CourseTask courseTask = convertQueryResultToPojo(record);
        return courseTask;
    }

    @Override
    public CourseTask update(CourseTask entry) {
        CourseTaskRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK, entry);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK)
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("COURSE_TASK update count = %d", updateCount));
        return findById(entry.getId());
    }

    @Override
    public List<CourseTask> findByCourseId(Integer courseId) {
        List<CourseTaskRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK)
                .where(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK.COURSE_ID.eq(courseId))
                .orderBy(com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK.CREATED_TIME.asc())
                .fetchInto(CourseTaskRecord.class);
        List<CourseTask> entries = convertQueryResultToPojos(queryResults);
        LOGGER.debug(String.format("Found [%d] CourseTask entries", entries.size()));
        return entries;
    }


    @Override
    protected Table<CourseTaskRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.CourseTask.COURSE_TASK;
    }

    @Override
    protected Class<CourseTask> getPojoClass() {
        return CourseTask.class;
    }
}
