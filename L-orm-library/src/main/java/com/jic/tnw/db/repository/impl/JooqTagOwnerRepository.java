package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.TagOwner;
import com.jic.tnw.db.mysql.tables.records.TagOwnerRecord;
import com.jic.tnw.db.repository.TagOwnerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JooqTagOwnerRepository extends AbstractJooqRepository<TagOwner, TagOwnerRecord> implements TagOwnerRepository {
    private static final Log LOGGER = LogFactory.getLog(JooqTagRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqTagOwnerRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public TagOwner add(TagOwner entry){
        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //INSERT INTO tag_owner (ownerType, ownerId, tagId, userId, createdTime) VALUES ('course-set', '34', '3', '3', '1523600556')
        TagOwnerRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER, entry);
        TagOwnerRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER)
                .set(record)
                .returning()
                .fetchOne();
        TagOwner returned = convertQueryResultToPojo(persisted);
        return returned;
    }
    @Override
    public TagOwner delete(Integer id){
        Integer deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER)
                .where(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER.ID.eq(id))
                .execute();
        LOGGER.info(String.format("delete count tag owner = %d",deleteCount));
        return findById(id);
    }
    @Override
    public List<TagOwner> findAll(){
        return null;
    }
    @Override
    public TagOwner findById(Integer id){
        TagOwnerRecord tagOwnerRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER)
                .where(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER.ID.eq(id))
                .fetchOne();
        TagOwner tagOwner = convertQueryResultToPojo(tagOwnerRecord);
        return tagOwner;
    }
    @Override
    public TagOwner update(TagOwner entry){
        TagOwnerRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER, entry);


        return null;
    }
    @Override
    public List<TagOwner> findByOwnIdAndOwnType(Integer ownId,String OwnType){
        List<TagOwnerRecord> tagOwnerRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER)
                .where(com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER.OWNER_ID.eq(ownId), com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER.OWNER_TYPE.eq(OwnType))
                .fetch();
        List<TagOwner> tagOwners = convertQueryResultToPojos(tagOwnerRecords);
        return tagOwners;
    }


    @Override
    protected Table<TagOwnerRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.TagOwner.TAG_OWNER;
    }

    @Override
    protected Class<TagOwner> getPojoClass() {
        return TagOwner.class;
    }
}
