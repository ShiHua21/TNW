package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.CourseChapter;
import com.jic.tnw.db.mysql.tables.records.CourseChapterRecord;
import com.jic.tnw.db.repository.CourseChapterRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author lee5hx
 * @date 2018/04/16
 */
@Repository
public class JooqCourseChapterRepository extends AbstractJooqRepository<CourseChapter, CourseChapterRecord> implements CourseChapterRepository {


    private static final Log LOGGER = LogFactory.getLog(JooqCourseChapterRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqCourseChapterRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public CourseChapter add(CourseChapter entry) {
        CourseChapterRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        CourseChapter returned = convertQueryResultToPojo(persisted);
        return returned;
    }
    private CourseChapterRecord createRecord(CourseChapter entry) {
        return jooq.newRecord(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER,entry);
    }

    @Override
    public CourseChapter delete(Integer id) {
        CourseChapter courseChapter = findById(id);
        Integer delCount = jooq.delete(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER)
                .where(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER.ID.eq(id))
                .execute();
        LOGGER.info(String.format("course chapter delete count = %s",delCount));
        return courseChapter;
    }

    @Override
    public List<CourseChapter> findAll() {
        return null;
    }

    @Override
    public CourseChapter findById(Integer id) {
        CourseChapterRecord record = jooq.selectFrom(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER)
                .where(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER.ID.eq(id))
                .fetchOne();
        CourseChapter courseChapter = convertQueryResultToPojo(record);
        return courseChapter;
    }

    @Override
    public CourseChapter update(CourseChapter entry) {
        CourseChapterRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER, entry);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER)
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("COURSE_CHAPTER update count = %d", updateCount));
        return findById(entry.getId());
    }

    @Override
    public List<CourseChapter> findByCourseId(Integer courseId) {
        List<CourseChapterRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER)
                .where(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER.COURSE_ID.eq(courseId))
                .orderBy(com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER.SEQ.asc())
                .fetchInto(CourseChapterRecord.class);
        List<CourseChapter> entries = convertQueryResultToPojos(queryResults);
        LOGGER.debug(String.format("Found [%d] CourseChapter entries", entries.size()));
        return entries;
    }

    @Override
    protected Table<CourseChapterRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.CourseChapter.COURSE_CHAPTER;
    }

    @Override
    protected Class<CourseChapter> getPojoClass() {
        return CourseChapter.class;
    }
}
