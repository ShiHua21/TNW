package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.enums.CourseMemberRole;
import com.jic.tnw.db.mysql.tables.pojos.CourseMember;
import com.jic.tnw.db.mysql.tables.records.CourseMemberRecord;
import com.jic.tnw.db.repository.CourseMemberRepository;
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
public class JooqCourseMemberRepository extends AbstractJooqRepository<CourseMember, CourseMemberRecord> implements CourseMemberRepository {



    private static final Log LOGGER = LogFactory.getLog(JooqCourseMemberRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqCourseMemberRepository(DSLContext jooq) {
        this.jooq = jooq;
    }


    @Override
    public CourseMember add(CourseMember entry) {
        CourseMemberRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER)
                .set(createRecord(jooq,entry))
                .returning()
                .fetchOne();
        CourseMember returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    @Override
    public CourseMember delete(Integer id) {
        CourseMember courseMember = findById(id);
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("CourseMember delete count = %d",deleteCount));
        return courseMember;
    }

    @Override
    public List<CourseMember> findAll() {
        return null;
    }

    @Override
    public List<CourseMember> findTeachersByCourseId(Integer courseId) {
        List<CourseMemberRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER.ID.eq(courseId))
                .and(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER.ROLE.eq(CourseMemberRole.TEACHER))
                .fetchInto(CourseMemberRecord.class);
        return convertQueryResultToPojos(queryResults);
    }



    @Override
    public CourseMember findById(Integer id) {
        CourseMemberRecord courseRecord = jooq.selectFrom(getTable())
                .where(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER.ID.eq(id))
                .fetchOne();
        CourseMember courseMember = convertQueryResultToPojo(courseRecord);
        return courseMember;
    }

    @Override
    public CourseMember update(CourseMember entry) {
        CourseMemberRecord record = jooq.newRecord(getTable(), entry);
        resetChangedOnNotNull(record);
        Integer updateCount = jooq.update(getTable())
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER.ID.eq(entry.getId()))
                .execute();
        LOGGER.debug(String.format("CourseMember update count = %d",updateCount));
        return findById(entry.getId());
    }

    @Override
    protected Table<CourseMemberRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.CourseMember.COURSE_MEMBER;
    }

    @Override
    protected Class<CourseMember> getPojoClass() {
        return CourseMember.class;
    }
}
