package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Category;
import com.jic.tnw.db.mysql.tables.records.CategoryRecord;
import com.jic.tnw.db.repository.CategoryRepository;
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
public class JooqCategoryRepository extends AbstractJooqRepository<Category, CategoryRecord> implements CategoryRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqCategoryRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqCategoryRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Category add(Category entry) {
        CategoryRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Category.CATEGORY)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Category returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private CategoryRecord createRecord(Category entry) {
        CategoryRecord record = new CategoryRecord();
        record.setName(entry.getName());
        record.setCode(entry.getCode());
        record.setParentId(entry.getParentId());
        record.setGroupId(entry.getGroupId());
        record.setParentId(entry.getParentId());
        record.setIcon(entry.getIcon());
        record.setOrgCode(entry.getOrgCode());
        record.setOrgId(entry.getOrgId());
        record.setWeight(entry.getWeight());
        record.setPath(entry.getPath());
        record.setDescription(entry.getDescription());
        return record;
    }

    @Override
    public List<Category> findListByGroupId(Integer groupId) {
        LOGGER.debug("Finding Category entries.");
        List<CategoryRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Category.CATEGORY)
                .where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.GROUP_ID.eq(groupId))
                .orderBy(com.jic.tnw.db.mysql.tables.Category.CATEGORY.ID.asc())
                .fetchInto(CategoryRecord.class);
        List<Category> entries = convertQueryResultToPojos(queryResults);
        LOGGER.debug(String.format("Found [%d] Category entries", entries.size()));
        return entries;
    }

    @Override
    public void updateCtierCodeById(Integer Id, String ctierCode) {
        LOGGER.info(String.format("Update Category ById:[%d]", Id));
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Category.CATEGORY)
                .set(com.jic.tnw.db.mysql.tables.Category.CATEGORY.CTIER_CODE, ctierCode)
                .where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.ID.eq(Id))
                .execute();
        LOGGER.debug(String.format("Updated [%d] Category entry.", updatedRecordCount));

    }





    @Override
    public Category delete(Integer id) {
        return null;
    }

    @Override
    public void deleteLikeTierCode(String tierCode) {
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.Category.CATEGORY)
                .where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.CTIER_CODE.like(tierCode+"%"))
                .execute();
        LOGGER.debug(String.format("delete [%d] Category entry.", deletedRecordCount));
    }

    @Override
    public List<Category> findAll() {
        LOGGER.info("Finding all Category entries.");
        List<CategoryRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Category.CATEGORY).orderBy(com.jic.tnw.db.mysql.tables.Category.CATEGORY.ID.asc())
                .fetchInto(CategoryRecord.class);
        List<Category> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] Category entries", entries.size()));
        return entries;
    }

    @Override
    public Category findById(Integer id) {
        CategoryRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Category.CATEGORY).where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.ID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public Category update(Category entry) {

        LOGGER.info(String.format("Update Category ById:[%d]", entry.getId()));
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Category.CATEGORY)
                .set(com.jic.tnw.db.mysql.tables.Category.CATEGORY.NAME, entry.getName())
                .set(com.jic.tnw.db.mysql.tables.Category.CATEGORY.CODE, entry.getCode())
                .set(com.jic.tnw.db.mysql.tables.Category.CATEGORY.DESCRIPTION, entry.getDescription())
                .where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.ID.eq(entry.getId()))
                .execute();
        LOGGER.debug(String.format("updatedRecordCount [%d] Category entry.", updatedRecordCount));

        return findById(entry.getId());
    }

    @Override
    public Category findByCode(String code) {
        CategoryRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Category.CATEGORY).where(com.jic.tnw.db.mysql.tables.Category.CATEGORY.CODE.eq(code))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    protected Table<CategoryRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Category.CATEGORY;
    }

    @Override
    protected Class<Category> getPojoClass() {
        return Category.class;
    }
}
