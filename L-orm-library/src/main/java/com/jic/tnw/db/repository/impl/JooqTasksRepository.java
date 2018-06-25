package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.exception.DataNotFoundException;
import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import com.jic.tnw.db.mysql.tables.records.TasksRecord;
import com.jic.tnw.db.repository.TasksRepository;
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
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
@Repository
public class JooqTasksRepository extends AbstractJooqRepository<Tasks, TasksRecord> implements TasksRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqTasksRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqTasksRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Tasks add(Tasks entry) {
        TasksRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Tasks.TASKS)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Tasks returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private TasksRecord createRecord(Tasks entry) {
        TasksRecord record = new TasksRecord();
        record.setId(entry.getId());
        record.setName(entry.getName());
        record.setBeginTime(entry.getBeginTime());
        record.setEndTime(entry.getEndTime());
        record.setElapsed(entry.getElapsed());
        record.setTriggedUserName(entry.getTriggedUserName());
        record.setStatus(entry.getStatus());
        record.setResult(entry.getResult());
        return record;
    }


    @Override
    public Tasks delete(Integer id) {
        return null;
    }

    @Override
    public List<Tasks> findAll() {
        return null;
    }

    @Override
    public Tasks findById(Integer id) {
        LOGGER.info(String.format("Finding tasks entry by id: {%d}", id));
        TasksRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tasks.TASKS)
                .where(com.jic.tnw.db.mysql.tables.Tasks.TASKS.ID.equal(id))
                .fetchOne();
        if (queryResult == null) {
            throw new DataNotFoundException("No tasks entry found with id: " + id);
        }
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public Tasks update(Tasks entry) {
        LOGGER.info("Updating task");
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Tasks.TASKS)
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.NAME, entry.getName())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.BEGIN_TIME, entry.getBeginTime())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.END_TIME, entry.getEndTime())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.ELAPSED, entry.getElapsed())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.TRIGGED_USER_NAME, entry.getTriggedUserName())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.STATUS, entry.getStatus())
                .set(com.jic.tnw.db.mysql.tables.Tasks.TASKS.RESULT, entry.getResult())
                .where(com.jic.tnw.db.mysql.tables.Tasks.TASKS.ID.equal(entry.getId()))
                .execute();

        LOGGER.debug(String.format("Updated [%d] todo entry.", updatedRecordCount));

        return findById(entry.getId());
    }


    private long findCountByStatus(String name, String triggedUserName, TasksStatus... tasksStatus) {
        //LOGGER.debug(String.format("Finding search result count by using status: [%s]", tasksStatus));

        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.Tasks.TASKS)
                        .where(createWhereConditions(name, triggedUserName, tasksStatus))
        );
        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }


    private Condition createWhereConditions(String name, String triggedUserName, TasksStatus... tasksStatus) {
        Condition condition = com.jic.tnw.db.mysql.tables.Tasks.TASKS.NAME.isNotNull();

        if (tasksStatus != null && tasksStatus.length > 0) {
            condition = condition.and(com.jic.tnw.db.mysql.tables.Tasks.TASKS.STATUS.in(tasksStatus));
        }
        if (!StringUtils.isEmpty(name)) {
            condition = condition.and(com.jic.tnw.db.mysql.tables.Tasks.TASKS.NAME.eq(name));
        }
        if (!StringUtils.isEmpty(triggedUserName)) {
            condition = condition.and(com.jic.tnw.db.mysql.tables.Tasks.TASKS.TRIGGED_USER_NAME.eq(triggedUserName));
        }
        return condition;
    }


    @Override
    public Page<Tasks> find(Pageable pageable, String name, String triggedUserName, TasksStatus... tasksStatus) {
        LOGGER.info(String.format("Finding [%d] todo entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));


        //String likeExpression = "%" + name + "%";

        List<TasksRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tasks.TASKS)
                .where(createWhereConditions(name, triggedUserName, tasksStatus))
                .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.Tasks.TASKS, pageable.getSort()))
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetchInto(TasksRecord.class);

        List<Tasks> todoEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] todo entries for page: [%d]",
                todoEntries.size(),
                pageable.getPageNumber())
        );

        long totalCount = findCountByStatus(name, triggedUserName, tasksStatus);

        LOGGER.info(String.format("{} todo entries matches with the like expression: {}",
                totalCount,
                "")
        );


        return new PageImpl<>(todoEntries, pageable, totalCount);
    }

//    @Override
//    public Tasks convertQueryResultToPojo(TasksRecord queryResult) {
//        Tasks tasks = new Tasks();
//        tasks.setId(queryResult.getId());
//        tasks.setBeginTime(queryResult.getBeginTime());
//        tasks.setEndTime(queryResult.getEndTime());
//        tasks.setElapsed(queryResult.getElapsed());
//        tasks.setName(queryResult.getName());
//        tasks.setTriggedUserName(queryResult.getTriggedUserName());
//        tasks.setStatus(queryResult.getStatus());
//        tasks.setResult(queryResult.getResult());
//        return tasks;
//    }
//
//    @Override
//    public List<Tasks> convertQueryResultToPojos(List<TasksRecord> queryResults) {
//        List<Tasks> entries = new ArrayList<>();
//        for (TasksRecord queryResult : queryResults) {
//            entries.add(convertQueryResultToPojo(queryResult));
//        }
//        return entries;
//    }


    @Override
    protected Table<TasksRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Tasks.TASKS;
    }


    @Override
    protected Class<Tasks> getPojoClass() {
        return Tasks.class;
    }
}
