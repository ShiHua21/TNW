package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.ActivityText;
import com.jic.tnw.db.mysql.tables.records.ActivityTextRecord;
import com.jic.tnw.db.repository.ActivityTextRepository;
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
public class JooqActivityTextRepository extends AbstractJooqRepository<ActivityText, ActivityTextRecord> implements ActivityTextRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqActivityTextRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqActivityTextRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public ActivityText add(ActivityText entry) {
        return add(jooq,entry);
    }

    @Override
    public ActivityText delete(Integer id) {
        return null;
    }

    @Override
    public List<ActivityText> findAll() {
        return null;
    }

    @Override
    public ActivityText findById(Integer id) {
        return null;
    }

    @Override
    public ActivityText update(ActivityText entry) {
        return null;
    }

    @Override
    protected Table<ActivityTextRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.ActivityText.ACTIVITY_TEXT;
    }

    @Override
    protected Class<ActivityText> getPojoClass() {
        return ActivityText.class;
    }
}
