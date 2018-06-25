package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.CategoryGroup;
import com.jic.tnw.db.mysql.tables.records.CategoryGroupRecord;
import com.jic.tnw.db.repository.CategoryGroupRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
@Repository
public class JooqCategoryGroupRepository extends AbstractJooqRepository<CategoryGroup, CategoryGroupRecord> implements CategoryGroupRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqCategoryGroupRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqCategoryGroupRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public CategoryGroup add(CategoryGroup entry) {
        return null;
    }

    @Override
    public CategoryGroup delete(Integer id) {
        return null;
    }

    @Override
    public List<CategoryGroup> findAll() {
        LOGGER.debug("Finding all CategoryGroup entries.");
        List<CategoryGroupRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.CategoryGroup.CATEGORY_GROUP).orderBy(com.jic.tnw.db.mysql.tables.CategoryGroup.CATEGORY_GROUP.ID.asc())
                .fetchInto(CategoryGroupRecord.class);
        List<CategoryGroup> entries = convertQueryResultToPojos(queryResults);
        LOGGER.debug(String.format("Found [%d] CategoryGroup entries", entries.size()));
        return entries;
    }

    @Override
    public CategoryGroup findById(Integer id) {
        return null;
    }

    @Override
    public CategoryGroup update(CategoryGroup entry) {
        return null;
    }

    @Override
    protected Table<CategoryGroupRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.CategoryGroup.CATEGORY_GROUP;
    }

    @Override
    protected Class<CategoryGroup> getPojoClass() {
        return CategoryGroup.class;
    }
}
