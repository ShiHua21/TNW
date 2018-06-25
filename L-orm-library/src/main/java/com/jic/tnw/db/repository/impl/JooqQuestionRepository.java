package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Question;
import com.jic.tnw.db.mysql.tables.records.QuestionRecord;
import com.jic.tnw.db.repository.QuestionRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JooqQuestionRepository extends AbstractJooqRepository<Question,QuestionRecord> implements QuestionRepository {
    private static final Log LOGGER = LogFactory.getLog(JooqOrgRepository.class);


    private final DSLContext jooq;
    @Autowired
    public JooqQuestionRepository(DSLContext jooq) {
        this.jooq = jooq;
    }
    @Override
    public Question findById(Integer id){
        QuestionRecord record = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Question.QUESTION)
                .where(com.jic.tnw.db.mysql.tables.Question.QUESTION.ID.eq(id))
                .fetchOne();
        LOGGER.info(String.format("Question find by Id =%s",record));
        Question question = convertQueryResultToPojo(record);
        return question;
    }
    @Override
    public Question delete(Integer id) {
        Question deleted = findById(id);
        int delCount = jooq.delete(com.jic.tnw.db.mysql.tables.Question.QUESTION)
                .where(com.jic.tnw.db.mysql.tables.Question.QUESTION.ID.equal(id))
                .execute();
        LOGGER.info(String.format("DEL_Group_ID_Count ==== %S",delCount));
        return deleted;
    }
    @Override
    public List<Question> findAll() {
        LOGGER.info("Finding all Org entries.");
        List<QuestionRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Question.QUESTION).orderBy(com.jic.tnw.db.mysql.tables.Question.QUESTION.CREATED_TIME.asc())
                .fetchInto(QuestionRecord.class);
        List<Question> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] Org entries", entries.size()));
        return entries;
    }
    @Override
    public Question add(Question entry) {
        QuestionRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Question.QUESTION)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Question returned = convertQueryResultToPojo(persisted);
        return returned;
    }
    private QuestionRecord createRecord(Question entry) {
        return jooq.newRecord(com.jic.tnw.db.mysql.tables.Question.QUESTION,entry);
    }
    @Override
    public Question update(Question entry) {
        QuestionRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.Question.QUESTION, entry);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Question.QUESTION)
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.Question.QUESTION.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("COURSE_CHAPTER update count = %d", updateCount));
        return findById(entry.getId());
    }
    @Override
    protected Table<QuestionRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Question.QUESTION;
    }

    @Override
    protected Class<Question> getPojoClass() {
        return Question.class;
    }
}
