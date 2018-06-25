package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.tnw.db.mysql.tables.records.PostGroupRecord;
import com.jic.tnw.db.repository.PostGroupRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JooqPostGroupRepository extends AbstractJooqRepository<PostGroup, PostGroupRecord> implements PostGroupRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqOrgRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqPostGroupRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public PostGroup delete(Integer id) {

        PostGroup deleted = findById(id);
        int delCount = jooq.delete(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP)
                .where(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.ID.equal(id))
                .execute();
        LOGGER.info(String.format("DEL_Group_ID_Count ==== %S", delCount));
        return deleted;
    }

    @Override
    public List<PostGroup> findAll() {
        LOGGER.info("Finding all Org entries.");
        List<PostGroupRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP).orderBy(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.SEQ.asc())
                .fetchInto(PostGroupRecord.class);
        List<PostGroup> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] Org entries", entries.size()));
        return entries;
    }

    @Override
    public PostGroup add(PostGroup entry) {
        PostGroupRecord postGroupRecord = jooq.insertInto(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP)
                .set(createPostGroupRecord(entry))
                .returning()
                .fetchOne();
        PostGroup returned = convertQueryResultToPojo(postGroupRecord);

        return returned;
    }

    private PostGroupRecord createPostGroupRecord(PostGroup entry) {
        PostGroupRecord record = new PostGroupRecord();
        record.setName(entry.getName());
        record.setSeq(entry.getSeq());
        record.setCreatedUserId(entry.getCreatedUserId());
        record.setCreatedTime(entry.getCreatedTime());
        record.setLastUpdTime(entry.getLastUpdTime());
        return record;
    }


    @Override
    public PostGroup findById(Integer id) {

        PostGroupRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP)
                .where(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.ID.equal(id))
                .fetchOne();

        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public PostGroup update(PostGroup entry) {
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP)
                .set(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.NAME, entry.getName())
                .set(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.SEQ, entry.getSeq())
                .set(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.LAST_UPD_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.LAST_UPD_USER_ID, entry.getLastUpdUserId())
                .where(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.ID.equal(entry.getId()))
                .execute();
        LOGGER.debug(String.format("PostGroup update_time =====%s", updateCount));
        return findById(entry.getId());
    }

    @Override
    public PostGroup findByName(String name) {
        LOGGER.info(String.format("find_by_name --===%s", name));
        PostGroupRecord query_result = jooq.selectFrom(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP)
                .where(com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP.NAME.equal(name))
                .fetchOne();
        return convertQueryResultToPojo(query_result);
    }

    @Override
    public List<PostGroup> convertQueryResultToPojos(List<PostGroupRecord> queryResults) {
        List<PostGroup> entries = new ArrayList<>();
        PostGroup entry;
        for (PostGroupRecord queryResult : queryResults) {
            entry = convertQueryResultToPojo(queryResult);
            entries.add(entry);
        }
        return entries;
    }

    @Override
    public PostGroup convertQueryResultToPojo(PostGroupRecord queryResult) {
        LOGGER.info(String.format("queryResult--===%s", queryResult));
        if (queryResult == null) {
            return null;
        }
        PostGroup post = new PostGroup();
        post.setId(queryResult.getId());
        post.setName(queryResult.getName());
        post.setCreatedUserId(queryResult.getCreatedUserId());
        post.setSeq(queryResult.getSeq());
        post.setLastUpdTime(queryResult.getLastUpdTime());
        post.setCreatedTime(queryResult.getCreatedTime());
        return post;
    }

    @Override
    protected Table<PostGroupRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP;
    }

    @Override
    protected Class<PostGroup> getPojoClass() {
        return PostGroup.class;
    }
}
