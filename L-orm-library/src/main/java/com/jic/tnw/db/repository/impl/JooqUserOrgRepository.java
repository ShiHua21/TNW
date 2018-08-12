package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.UserOrg;
import com.jic.tnw.db.mysql.tables.records.UserOrgRecord;
import com.jic.tnw.db.repository.UserOrgRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JooqUserOrgRepository extends AbstractJooqRepository<UserOrg, UserOrgRecord> implements UserOrgRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqUserOrgRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqUserOrgRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserOrg add(UserOrg entry) {
        UserOrgRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        UserOrg returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private UserOrgRecord createRecord(UserOrg entry) {



        UserOrgRecord record = new UserOrgRecord();
        record.setOrgId(entry.getOrgId());
        record.setOrgCode(entry.getOrgCode());
        record.setUserId(entry.getUserId());
        record.setCreatedTime(entry.getCreatedTime());
        record.setCreatedUserId(entry.getCreatedUserId());
        record.setLastUpdTime(entry.getLastUpdTime());
        record.setLastUpdUserId(entry.getLastUpdUserId());
        //record.setUserId(entry.);
        record.setOrgCode(entry.getOrgCode());

        return record;
    }

    @Override
    public UserOrg delete(String id) {
        return null;
    }

    @Override
    public List<UserOrg> findAll() {
        return null;
    }

    @Override
    public UserOrg findById(String id) {
        int iid = Integer.parseInt(id);

        UserOrgRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG).where(com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG.ID.eq(iid))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public UserOrg update(UserOrg entry) {
        return null;
    }


    @Override
    public void deleteByUserId(Integer userId) {
        LOGGER.info(String.format("Deleting User.Org entry by user ID: [%d]", userId));
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG)
                .where(com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG.USER_ID.equal(userId))
                .execute();
        LOGGER.debug(String.format("Deleted [%d] User.Org entries", deletedRecordCount));
    }

//    @Override
//    public UserOrg convertQueryResultToPojo(UserOrgRecord queryResult) {
//        if (queryResult == null) {
//            return null;
//        }
//        return queryResult.into(UserOrg.class);
//    }
//
//    @Override
//    public List<UserOrg> convertQueryResultToPojos(List<UserOrgRecord> queryResults) {
//        List<UserOrg> entries = new ArrayList<>();
//        UserOrg entry;
//        for (UserOrgRecord queryResult : queryResults) {
//            entry = convertQueryResultToPojo(queryResult);
//            entries.add(entry);
//        }
//        return entries;
//    }


    @Override
    protected Table<UserOrgRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG;
    }

    @Override
    protected Class<UserOrg> getPojoClass() {
        return UserOrg.class;
    }
}
