package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.records.TagRecord;
import com.jic.tnw.db.repository.TagRepository;
import com.jic.tnw.db.repository.utils.JOOQPageSortUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/***
 * @author tp
 * @date 2018/4/2
 */
@Repository
public class JooqTagRepository extends AbstractJooqRepository<Tag, TagRecord> implements TagRepository {
    private static final Log LOGGER = LogFactory.getLog(JooqTagRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqTagRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Tag add(Tag entry) {
        TagRecord record = jooq.insertInto(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Tag tag = convertQueryResultToPojo(record);
        return tag;
    }

    private TagRecord createRecord(Tag entry) {
        TagRecord tagRecord = new TagRecord();
        tagRecord.setName(entry.getName());
//        tagRecord.setOrgCode(entry.getOrgCode());
//        tagRecord.setOrgId(entry.getOrgId());
        tagRecord.setCreatedTime(LocalDateTime.now());
        return tagRecord;
    }

    @Override
    public Tag delete(Integer id) {
        Tag tag = findById(id);
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .where(com.jic.tnw.db.mysql.tables.Tag.TAG.ID.eq(id))
                .execute();
        LOGGER.info(String.format("TAG DELETE COUNT %d", deleteCount));
        LOGGER.info(String.format("TAG DELETE FOUND BY ID %s", tag));
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        List<TagRecord> tagRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .orderBy(com.jic.tnw.db.mysql.tables.Tag.TAG.ID.asc())
                .fetchInto(TagRecord.class);
        List<Tag> tagList = convertQueryResultToPojos(tagRecords);
        return tagList;
    }

    @Override
    public Tag findById(Integer id) {
        TagRecord tagRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .where(com.jic.tnw.db.mysql.tables.Tag.TAG.ID.eq(id))
                .fetchOne();
        Tag tag = convertQueryResultToPojo(tagRecord);
        return tag;
    }

    @Override
    public Tag update(Tag entry) {
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .set(com.jic.tnw.db.mysql.tables.Tag.TAG.NAME, entry.getName())
                .where(com.jic.tnw.db.mysql.tables.Tag.TAG.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("TAG UPDATE COUNT %d", updateCount));

        return findById(entry.getId());
    }

//    @Override
//    public Tag convertQueryResultToPojo(TagRecord queryResult) {
//        if (queryResult == null) {
//            return null;
//        }
//        Tag tag = new Tag();
//        tag.setId(queryResult.getId());
//        tag.setCreatedTime(queryResult.getCreatedTime());
//        tag.setName(queryResult.getName());
////        tag.setOrgCode(queryResult.getOrgCode());
////        tag.setOrgId(queryResult.getOrgId());
//        return tag;
//    }
//
//    @Override
//    public List<Tag> convertQueryResultToPojos(List<TagRecord> queryResults) {
//        List<Tag> tagEntries = new ArrayList<>();
//        for (TagRecord tagRecord : queryResults) {
//            Tag tag = convertQueryResultToPojo(tagRecord);
//            tagEntries.add(tag);
//        }
//        return tagEntries;
//    }

    @Override
    public Tag findByName(String name) {
        LOGGER.info(String.format("find by name for tag name is %s", name));
        TagRecord tagRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .where(com.jic.tnw.db.mysql.tables.Tag.TAG.NAME.equal(name))
                .fetchOne();
        return convertQueryResultToPojo(tagRecord);
    }

    @Override
    public List<Tag> findByContainsTrueName(String name) {
        List<TagRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .where(com.jic.tnw.db.mysql.tables.Tag.TAG.NAME.like(name + "%"))
                .fetchInto(TagRecord.class);
        return convertQueryResultToPojos(queryResults);
    }

    @Override
    public Page<Tag> findWithPageable(Pageable pageable) {
        LOGGER.info(String.format("Finding [%d] todo entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));

        List<TagRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Tag.TAG)
                .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.Tag.TAG, pageable.getSort()))
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetchInto(TagRecord.class);
        List<Tag> todoEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] todo entries for page: [%d]",
                todoEntries.size(),
                pageable.getPageNumber())
        );
        long totalCount = findCountByStatus();
        return new PageImpl<>(todoEntries, pageable, totalCount);

    }

    private long findCountByStatus() {
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.Tag.TAG)
        );
        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }


    @Override
    protected Table<TagRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Tag.TAG;
    }

    @Override
    protected Class<Tag> getPojoClass() {
        return Tag.class;
    }
}
