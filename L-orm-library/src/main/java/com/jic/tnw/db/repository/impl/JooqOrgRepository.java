package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Org;
import com.jic.tnw.db.mysql.tables.records.OrgRecord;
import com.jic.tnw.db.repository.OrgRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JooqOrgRepository extends AbstractJooqRepository<Org, OrgRecord> implements OrgRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqOrgRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqOrgRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Org add(Org entry) {
        OrgRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.Org.ORG)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Org returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private OrgRecord createRecord(Org entry) {
        OrgRecord record = new OrgRecord();
        // record.setId(entry.getId());
        record.setBranchNm(entry.getBranchNm());
        record.setBranchNo(entry.getBranchNo());
        record.setParentId(entry.getParentId());
        record.setChildrenNum(entry.getChildrenNum());
        record.setCreatedTime(entry.getCreatedTime());
        record.setCreatedUserId(entry.getCreatedUserId());
        record.setDepth(entry.getDepth());
        //record.setOrgCode(entry.getOrgCode());
        //record.setLastUpdTime(entry.getLastUpdTime());
        record.setSeq(entry.getSeq());
        //record.setSyncId(entry.getSyncId());
        //record.setChildrenNum();
        record.setDescription(entry.getDescription());

        return record;
    }

    @Override
    public Org delete(String id) {
        LOGGER.info(String.format("Deleting Org entry by id: [%d]", id));
        Org deleted = findById(id);
        int iid = Integer.parseInt(id);
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.Org.ORG)
                .where(com.jic.tnw.db.mysql.tables.Org.ORG.ID.equal(iid))
                .execute();
        LOGGER.debug(String.format("Deleted [%d] todo entries", deletedRecordCount));
        LOGGER.info(String.format("Returning deleted Org entry:%s ", deleted));
        return deleted;
    }

    @Override
    public List<Org> findAll() {
        LOGGER.info("Finding all Org entries.");
        List<OrgRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Org.ORG).orderBy(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NO.asc())
                .fetchInto(OrgRecord.class);
        List<Org> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] Org entries", entries.size()));
        return entries;
    }

    @Override
    public List<Org> findByContainsName(String name) {
        List<OrgRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.Org.ORG)
                .where(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NM.like(name + "%"))
                .fetchInto(OrgRecord.class);
        return convertQueryResultToPojos(queryResults);
    }


    @Override
    public Org findById(String id) {
        int iid = Integer.parseInt(id);
        OrgRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Org.ORG).where(com.jic.tnw.db.mysql.tables.Org.ORG.ID.eq(iid))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public void updateChildrenNumById(Integer id, Integer childrenNum) {
        LOGGER.info(String.format("Update Org.ChildrenNum ById:[%d]", id));
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Org.ORG)
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.CHILDREN_NUM, childrenNum)
                .where(com.jic.tnw.db.mysql.tables.Org.ORG.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Updated [%d] Org.ChildrenNum entry.", updatedRecordCount));
        //return findById(id);
    }

    @Override
    public void updateOrgCodeById(Integer id, String orgCode) {
        LOGGER.info(String.format("Update Org.OrgCode ById:[%d]", id));
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Org.ORG)
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NO, orgCode)
                .where(com.jic.tnw.db.mysql.tables.Org.ORG.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Updated [%d] Org.OrgCode entry.", updatedRecordCount));
        //return findById(id);
    }


    @Override
    public Org findByCode(String code) {
        OrgRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Org.ORG).where(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NO.eq(code))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public Org update(Org entry) {
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.Org.ORG)
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NM, entry.getBranchNm())
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.BRANCH_NO, entry.getBranchNo())
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.DESCRIPTION, entry.getDescription())
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.LAST_UPD_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.Org.ORG.LAST_UPD_USER_ID, entry.getLastUpdUserId())
                .where(com.jic.tnw.db.mysql.tables.Org.ORG.ID.equal(entry.getId()))
                .execute();
        LOGGER.debug(String.format("Updated %d Org entry.", updatedRecordCount));
        return findById(entry.getId().toString());
    }


    @Override
    protected Table<OrgRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Org.ORG;
    }

    @Override
    protected Class<Org> getPojoClass() {
        return Org.class;
    }

}
