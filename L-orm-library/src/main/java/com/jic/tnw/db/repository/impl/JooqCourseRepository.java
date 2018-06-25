package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Course;
import com.jic.tnw.db.mysql.tables.records.CourseRecord;
import com.jic.tnw.db.repository.CourseRepository;
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
public class JooqCourseRepository extends AbstractJooqRepository<Course, CourseRecord> implements CourseRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqCourseSetRepository.class);
    private final DSLContext jooq;

    @Autowired
    public JooqCourseRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Course add(Course entry) {
        CourseRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Course.COURSE)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Course returned = convertQueryResultToPojo(persisted);
        return returned;
    }


    private CourseRecord createRecord(Course entry) {
        CourseRecord record = new CourseRecord();
        record.setCourseSetId(entry.getCourseSetId());
        record.setTitle(entry.getTitle());
        record.setExpiryMode(entry.getExpiryMode());
        record.setLearnMode(entry.getLearnMode());
        record.setCourseType(entry.getCourseType());
        record.setIsDefault(entry.getIsDefault());
        record.setIsFree(entry.getIsFree());
        record.setSerializeMode(entry.getSerializeMode());
        record.setType(entry.getType());
        record.setMaxRate(entry.getMaxRate());
        record.setStatus(entry.getStatus());
        record.setCreator(entry.getCreator());
        record.setCreatedTime(entry.getCreatedTime());
        record.setUpdatedTime(entry.getUpdatedTime());
        return record;
    }

    @Override
    public Course delete(Integer id) {
        return null;
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public Course findById(Integer id) {
        CourseRecord courseRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Course.COURSE)
                .where(com.jic.tnw.db.mysql.tables.Course.COURSE.ID.eq(id))
                .fetchOne();
        Course course = convertQueryResultToPojo(courseRecord);
        return course;
    }



    @Override
    public Course update(Course entry) {
    // UPDATE course_v8 SET categoryId = '17', updatedTime = '1523600556' WHERE courseSetId = '34
    // UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', updatedTime = '1523600556' WHERE id = '34'
        CourseRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.Course.COURSE, entry);
        resetChangedOnNotNull(record);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Course.COURSE)
//                .set(COURSE.CATEGORY_ID, entry.getCategoryId())
//                .set(COURSE.TITLE, entry.getTitle())
//                .set(COURSE.UPDATED_TIME, entry.getUpdatedTime())
//                .set(COURSE.SERIALIZE_MODE, entry.getSerializeMode())
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.Course.COURSE.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("course update count = %d", updateCount));

        return findById(entry.getId());
    }
    @Override
    public Course updateByDetail(Course entry){
        CourseRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.Course.COURSE, entry);

        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Course.COURSE)
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.SUMMARY,entry.getSummary())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.GOALS, entry.getGoals())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.UPDATED_TIME, entry.getUpdatedTime())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.AUDIENCES, entry.getAudiences())
//                .set(record)
                .where(com.jic.tnw.db.mysql.tables.Course.COURSE.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("course update count = %d", updateCount));

        return findById(entry.getId());
    }
    @Override
    public Course updateBySet(Course entry){
        //UPDATE course_v8 SET enableFinish = '0', buyable = '1', isFree = '1', originPrice = '0.00', tryLookable = '0', price = '0', coinPrice = '0.00', tryLookLength = '0', buyExpiryTime = '0', updatedTime = '1523859790' WHERE id = '34'
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Course.COURSE)
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.ENABLE_FINISH,entry.getEnableFinish())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.BUYABLE, entry.getBuyable())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.IS_FREE, entry.getIsFree())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.ORIGIN_PRICE, entry.getOriginPrice())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.TRY_LOOKABLE,entry.getTryLookable())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.PRICE,entry.getPrice())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.COIN_PRICE,entry.getCoinPrice())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.TRY_LOOK_LENGTH,entry.getTryLookLength())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.BUY_EXPIRY_TIME,entry.getBuyExpiryTime())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.UPDATED_TIME,entry.getUpdatedTime())
                .where(com.jic.tnw.db.mysql.tables.Course.COURSE.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("course update count = %d", updateCount));

        return findById(entry.getId());
    }
    @Override
    public Course updateByPublished(Course entry){
        //UPDATE course_v8 SET status = 'published', updatedTime = '1524446035' WHERE id = '35'

        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Course.COURSE)
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.STATUS,entry.getStatus())
                .set(com.jic.tnw.db.mysql.tables.Course.COURSE.UPDATED_TIME,entry.getUpdatedTime())
                .where(com.jic.tnw.db.mysql.tables.Course.COURSE.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("course update count = %d", updateCount));
        return findById(entry.getId());
    }


    @Override
    protected Table<CourseRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Course.COURSE;
    }

    @Override
    protected Class<Course> getPojoClass() {
        return Course.class;
    }
}
