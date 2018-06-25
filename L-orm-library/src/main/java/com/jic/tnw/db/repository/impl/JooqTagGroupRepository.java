package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.tnw.db.mysql.tables.records.TagGroupRecord;
import com.jic.tnw.db.repository.TagGroupRepository;
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
public class JooqTagGroupRepository extends AbstractJooqRepository<TagGroup, TagGroupRecord> implements TagGroupRepository {
    private static final Log LOGGER = LogFactory.getLog(JooqTagGroupRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqTagGroupRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public TagGroup add(TagGroup entry) {
        TagGroupRecord record = jooq.insertInto(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        TagGroup tagGroup = convertQueryResultToPojo(record);
        return tagGroup;
    }

    private TagGroupRecord createRecord(TagGroup entry) {
        TagGroupRecord tagGroupRecord = new TagGroupRecord();
        tagGroupRecord.setName(entry.getName());
        tagGroupRecord.setScope(entry.getScope());
        tagGroupRecord.setTagNum(entry.getTagNum());
        tagGroupRecord.setCreatedTime(LocalDateTime.now());
        tagGroupRecord.setUpdatedTime(LocalDateTime.now());

        return tagGroupRecord;
    }

    @Override
    public TagGroup delete(Integer id) {
        TagGroup tagGroup = findById(id);
        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .where(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.ID.eq(id))
                .execute();
        LOGGER.info(String.format("TAG DELETE COUNT %d", deleteCount));
        LOGGER.info(String.format("TAG DELETE FOUND BY ID %s", tagGroup));
        return tagGroup;
    }

    @Override
    public List<TagGroup> findAll() {

        List<TagGroupRecord> tagGroupRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .orderBy(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.ID.asc())
                .fetchInto(TagGroupRecord.class);
        List<TagGroup> tagList = convertQueryResultToPojos(tagGroupRecords);
        return tagList;
    }

    @Override
    public TagGroup findById(Integer id) {
        TagGroupRecord tagGroupRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .where(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.ID.eq(id))
                .fetchOne();
        TagGroup tagGroup = convertQueryResultToPojo(tagGroupRecord);
        return tagGroup;
    }

    @Override
    public TagGroup update(TagGroup entry) {
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.NAME, entry.getName())
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.SCOPE, entry.getScope())
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.TAG_NUM, entry.getTagNum())
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.UPDATED_TIME, LocalDateTime.now())
                .where(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("TAG UPDATE COUNT %d", updateCount));

        return findById(entry.getId());
    }
    @Override
    public TagGroup updateTagNum(Integer id){
        TagGroup tagGroup = findById(id);
        Integer tagNum = tagGroup.getTagNum();
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.TAG_NUM,tagNum-1)
                .set(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.UPDATED_TIME, LocalDateTime.now())
                .where(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.ID.eq(id))
                .execute();
        LOGGER.info(String.format("TAG UPDATE COUNT %d", updateCount));

        return findById(id);
    }

//    @Override
//    public TagGroup convertQueryResultToPojo(TagGroupRecord queryResult) {
//
//        if (queryResult == null) {
//            return null;
//        }
//        TagGroup tagGroup = new TagGroup();
//        tagGroup.setId(queryResult.getId());
//        tagGroup.setCreatedTime(queryResult.getCreatedTime());
//        tagGroup.setName(queryResult.getName());
//        tagGroup.setScope(queryResult.getScope());
//        tagGroup.setTagNum(queryResult.getTagNum());
//        return tagGroup;
//    }
//
//    @Override
//    public List<TagGroup> convertQueryResultToPojos(List<TagGroupRecord> queryResults) {
//        List<TagGroup> tagGroupEntries = new ArrayList<>();
//        for (TagGroupRecord tagGroupRecord : queryResults) {
//            TagGroup tagGroup = convertQueryResultToPojo(tagGroupRecord);
//            tagGroupEntries.add(tagGroup);
//        }
//        return tagGroupEntries;
//    }

    @Override
    public TagGroup findByName(String name) {
        LOGGER.info(String.format("find by name for tag name is %s", name));
        TagGroupRecord tagGroupRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .where(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP.NAME.equal(name))
                .fetchOne();
        return convertQueryResultToPojo(tagGroupRecord);
    }
    @Override
    public Page<TagGroup> findWithPageable(Pageable pageable){
        LOGGER.info(String.format("Finding [%d] todo entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));

        List<TagGroupRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
                .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP, pageable.getSort()))
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetchInto(TagGroupRecord.class);
        List<TagGroup> todoEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] todo entries for page: [%d]",
                todoEntries.size(),
                pageable.getPageNumber())
        );
        long totalCount = findCountByStatus();
        return new PageImpl<>(todoEntries, pageable, totalCount);
    }
    private long findCountByStatus(){
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP)
        );
        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }


    @Override
    protected Table<TagGroupRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.TagGroup.TAG_GROUP;
    }


    @Override
    protected Class<TagGroup> getPojoClass() {
        return TagGroup.class;
    }
}
