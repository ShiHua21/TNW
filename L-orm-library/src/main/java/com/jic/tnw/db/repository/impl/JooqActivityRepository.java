package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Activity;
import com.jic.tnw.db.mysql.tables.records.ActivityRecord;
import com.jic.tnw.db.repository.ActivityRepository;
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
public class JooqActivityRepository extends AbstractJooqRepository<Activity, ActivityRecord> implements ActivityRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqActivityRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqActivityRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Activity add(Activity entry) {
        ActivityRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Activity.ACTIVITY)
                .set(createRecord(jooq,entry))
                .returning()
                .fetchOne();
        Activity returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    @Override
    public Activity delete(Integer id) {
        return null;
    }

    @Override
    public List<Activity> findAll() {
        return null;
    }

    @Override
    public Activity findById(Integer id) {
        return null;
    }

    @Override
    public Activity update(Activity entry) {
        return null;
    }

    @Override
    protected Table<ActivityRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Activity.ACTIVITY;
    }

    @Override
    protected Class<Activity> getPojoClass() {
        return Activity.class;
    }
}
