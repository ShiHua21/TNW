/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.Tasks;
import com.jic.tnw.db.mysql.tables.records.TasksRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ???
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class TasksDao extends DAOImpl<TasksRecord, com.jic.tnw.db.mysql.tables.pojos.Tasks, Integer> {

    /**
     * Create a new TasksDao without any configuration
     */
    public TasksDao() {
        super(Tasks.TASKS, com.jic.tnw.db.mysql.tables.pojos.Tasks.class);
    }

    /**
     * Create a new TasksDao with an attached configuration
     */
    @Autowired
    public TasksDao(Configuration configuration) {
        super(Tasks.TASKS, com.jic.tnw.db.mysql.tables.pojos.Tasks.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.Tasks object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchById(Integer... values) {
        return fetch(Tasks.TASKS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.Tasks fetchOneById(Integer value) {
        return fetchOne(Tasks.TASKS.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByName(String... values) {
        return fetch(Tasks.TASKS.NAME, values);
    }

    /**
     * Fetch records that have <code>begin_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByBeginTime(LocalDateTime... values) {
        return fetch(Tasks.TASKS.BEGIN_TIME, values);
    }

    /**
     * Fetch records that have <code>end_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByEndTime(LocalDateTime... values) {
        return fetch(Tasks.TASKS.END_TIME, values);
    }

    /**
     * Fetch records that have <code>elapsed IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByElapsed(Long... values) {
        return fetch(Tasks.TASKS.ELAPSED, values);
    }

    /**
     * Fetch records that have <code>trigged_user_name IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByTriggedUserName(String... values) {
        return fetch(Tasks.TASKS.TRIGGED_USER_NAME, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByStatus(TasksStatus... values) {
        return fetch(Tasks.TASKS.STATUS, values);
    }

    /**
     * Fetch records that have <code>result IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Tasks> fetchByResult(String... values) {
        return fetch(Tasks.TASKS.RESULT, values);
    }
}
