package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.TagGroupTag;
import com.jic.tnw.db.mysql.tables.records.TagGroupTagRecord;
import com.jic.tnw.db.repository.TagGroupTagRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tp
 * @date 2018/4/3
 */
@Repository

public class JooqTagGroupTagRepository extends AbstractJooqRepository<TagGroupTag, TagGroupTagRecord> implements TagGroupTagRepository {
    private static final Log LOGGER = LogFactory.getLog(JooqTagGroupRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqTagGroupTagRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public TagGroupTag add(TagGroupTag entry) {
        TagGroupTagRecord record = jooq.insertInto(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        TagGroupTag tagGroupTag = convertQueryResultToPojo(record);
        return tagGroupTag;
    }

    private TagGroupTagRecord createRecord(TagGroupTag entry) {
        TagGroupTagRecord tagGroupTagRecord = new TagGroupTagRecord();
        tagGroupTagRecord.setTagId(entry.getTagId());
        tagGroupTagRecord.setGroupId(entry.getGroupId());
        return tagGroupTagRecord;
    }

    @Override
    public TagGroupTag delete(Integer id) {
        TagGroupTag tagGroupTag = findById(id);
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.ID.eq(id))
                .execute();
        LOGGER.info(String.format("TAG DELETE COUNT %d", deleteCount));
        return tagGroupTag;
    }

    @Override
    public List<TagGroupTag> findAll() {

        List<TagGroupTagRecord> tagGroupRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .orderBy(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.ID.asc())
                .fetchInto(TagGroupTagRecord.class);
        List<TagGroupTag> tagList = convertQueryResultToPojos(tagGroupRecords);
        return tagList;
    }

    @Override
    public TagGroupTag findById(Integer id) {
        TagGroupTagRecord tagGroupTagRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.ID.eq(id))
                .fetchOne();
        TagGroupTag tagGroupTag = convertQueryResultToPojo(tagGroupTagRecord);
        return tagGroupTag;
    }
    @Override
    public TagGroupTag deleteByTagId(Integer id){
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.TAG_ID.eq(id))
                .execute();
        return foundbyTagId(id);
    }

    private TagGroupTag foundbyTagId(Integer tagId){
        TagGroupTagRecord tagGroupTagRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.TAG_ID.eq(tagId))
                .fetchOne();
        TagGroupTag tagGroupTag = convertQueryResultToPojo(tagGroupTagRecord);
        return tagGroupTag;
    }
    private TagGroupTag foundbyTagGroupId(Integer tagGroupId){
        TagGroupTagRecord tagGroupTagRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.GROUP_ID.eq(tagGroupId))
                .fetchOne();
        TagGroupTag tagGroupTag = convertQueryResultToPojo(tagGroupTagRecord);
        return tagGroupTag;
    }
    @Override
    public TagGroupTag update(TagGroupTag entry) {
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .set(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.TAG_ID, entry.getTagId())
                .set(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.GROUP_ID, entry.getGroupId())
                .execute();
        LOGGER.info(String.format("TAG UPDATE COUNT %d", updateCount));
        return findById(entry.getId());
    }

//    @Override
//    public TagGroupTag convertQueryResultToPojo(TagGroupTagRecord queryResult) {
//
//        if (queryResult == null) {
//            return null;
//        }
//        TagGroupTag tagGroupTag = new TagGroupTag();
//        tagGroupTag.setId(queryResult.getId());
//        tagGroupTag.setGroupId(queryResult.getGroupId());
//        tagGroupTag.setTagId(queryResult.getTagId());
//        return tagGroupTag;
//    }
//
//    @Override
//    public List<TagGroupTag> convertQueryResultToPojos(List<TagGroupTagRecord> queryResults) {
//        List<TagGroupTag> tagGroupTagEntries = new ArrayList<>();
//        for (TagGroupTagRecord tagGroupTagRecord : queryResults) {
//            TagGroupTag tagGroupTag = convertQueryResultToPojo(tagGroupTagRecord);
//            tagGroupTagEntries.add(tagGroupTag);
//        }
//        return tagGroupTagEntries;
//    }
    @Override
    public List<Integer> findByTagGroupId(Integer id){
        List<TagGroupTagRecord>tagGroupTagRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.GROUP_ID.eq(id))
                .fetch();
        List<TagGroupTag>tagGroupTagList = convertQueryResultToPojos(tagGroupTagRecords);
        List<Integer>tagIdList = new ArrayList<>();
        for (TagGroupTag tagGroupTag:tagGroupTagList){
            tagIdList.add(tagGroupTag.getTagId());
        }
        return tagIdList;

    }
    @Override
    public List<Integer> findByTagId(Integer id){
        List<TagGroupTagRecord>tagGroupTagRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.TAG_ID.eq(id))
                .fetch();
        List<TagGroupTag>tagGroupTagList = convertQueryResultToPojos(tagGroupTagRecords);
        List<Integer>tagGroupIdList = new ArrayList<>();
        for (TagGroupTag tagGroupTag:tagGroupTagList){
            tagGroupIdList.add(tagGroupTag.getGroupId());
        }
        return tagGroupIdList;
    }
    @Override
    public List<Integer> findByTagIdGetTagNum(Integer id){
        List<TagGroupTagRecord>tagGroupTagRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.TAG_ID.eq(id))
                .fetch();
        List<TagGroupTag>tagGroupTagList = convertQueryResultToPojos(tagGroupTagRecords);
        List<Integer>tagIdList = new ArrayList<>();
        for (TagGroupTag tagGroupTag:tagGroupTagList){
            tagIdList.add(tagGroupTag.getId());
        }
        return tagIdList;
    }
    @Override
    public List<Integer> findByTagGroupIdGetTagGroupNum(Integer id){

        List<TagGroupTagRecord>tagGroupTagRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.GROUP_ID.eq(id))
                .fetch();
        List<TagGroupTag>tagGroupTagList = convertQueryResultToPojos(tagGroupTagRecords);
        List<Integer>tagGroupIdList = new ArrayList<>();
        for (TagGroupTag tagGroupTag:tagGroupTagList){
            tagGroupIdList.add(tagGroupTag.getId());
        }
        return tagGroupIdList;
    }
    @Override
    public TagGroupTag deleteByTagGroupId(Integer id){
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG)
                .where(com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG.GROUP_ID.eq(id))
                .execute();
        return foundbyTagGroupId(id);
    }


    @Override
    protected Table<TagGroupTagRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.TagGroupTag.TAG_GROUP_TAG;
    }


    @Override
    protected Class<TagGroupTag> getPojoClass() {
        return TagGroupTag.class;
    }
}
