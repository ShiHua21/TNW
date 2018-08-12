package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.UserGroup;
import com.jic.tnw.db.mysql.tables.records.UserGroupRecord;
import com.jic.tnw.db.repository.UserGroupRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JooqUserGroupRepository extends AbstractJooqRepository<UserGroup, UserGroupRecord> implements UserGroupRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqUserGroupRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqUserGroupRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserGroup add(UserGroup entry) {
        UserGroupRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        UserGroup returned = convertQueryResultToPojo(persisted);
        return returned;
    }


    private UserGroupRecord createRecord(UserGroup entry) {
        UserGroupRecord record = new UserGroupRecord();
        record.setCode(entry.getCode());
        record.setDescription(entry.getDescription());
        record.setName(entry.getName());
        record.setCreatedTime(entry.getCreatedTime());
        record.setCreatedUserId(entry.getCreatedUserId());
        return record;
    }

    @Override
    public UserGroup delete(String id) {
        LOGGER.info(String.format("Deleting UserGroup entry by id: [%d]", id));
        int iid = Integer.parseInt(id);

        UserGroup deleted = findById(id);
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP)
                .where(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.ID.equal(iid))
                .execute();
        LOGGER.debug(String.format("Deleted [%d] UserGroup entries", deletedRecordCount));
        LOGGER.info(String.format("Returning deleted UserGroup entry:%s ", deleted));
        return deleted;
    }

    @Override
    public List<UserGroup> findAll() {
        LOGGER.info("Finding all userGroup entries.");
        List<UserGroupRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP).fetchInto(UserGroupRecord.class);
        List<UserGroup> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%s] userGroup entries", entries.size()));
        return entries;
    }

    @Override
    public UserGroup findById(String id) {
        int iid = Integer.parseInt(id);

        UserGroupRecord result = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP)
                .where(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.ID.eq(iid))
                .fetchOne();
        return convertQueryResultToPojo(result);
    }

    @Override
    public UserGroup findByCode(String code) {
        UserGroupRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP).where(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.CODE.eq(code))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }


    @Override
    public UserGroup update(UserGroup entry) {
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP)
                .set(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.NAME, entry.getName())
                .set(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.CODE, entry.getCode())
                .set(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.DESCRIPTION, entry.getDescription())
                .set(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.LAST_UPD_TIME, entry.getLastUpdTime())
                .set(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.LAST_UPD_USER_ID, entry.getLastUpdUserId())
                .where(com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP.ID.equal(entry.getId()))
                .execute();
        LOGGER.debug(String.format("Updated %d UserGroup entry.", updatedRecordCount));
        return findById(entry.getId().toString());
    }

    @Override
    protected Table<UserGroupRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP;
    }

    @Override
    protected Class<UserGroup> getPojoClass() {
        return UserGroup.class;
    }

    //    @Override
//    public UserGroup convertQueryResultToPojo(UserGroupRecord queryResult) {
//        if (queryResult == null) {
//            return null;
//        }
//        return queryResult.into(UserGroup.class);
//
//    }
//
//    @Override
//    public List<UserGroup> convertQueryResultToPojos(List<UserGroupRecord> queryResults) {
//        List<UserGroup> entries = new ArrayList<>();
//        for (UserGroupRecord queryResult : queryResults) {
//            UserGroup entry = convertQueryResultToPojo(queryResult);
//            entries.add(entry);
//        }
//        return entries;
//    }
}
