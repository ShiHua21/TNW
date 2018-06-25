package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Post;

import com.jic.tnw.db.mysql.tables.records.PostRecord;
import com.jic.tnw.db.repository.PostRepository;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JooqPostRepository extends AbstractJooqRepository<Post,PostRecord> implements PostRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqOrgRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqPostRepository(DSLContext jooq) {
        this.jooq = jooq;
    }
    @Override
    public Post delete(Integer id) {
        Post deleted = findById(id);
        int delCount = jooq.delete(com.jic.tnw.db.mysql.tables.Post.POST)
                .where(com.jic.tnw.db.mysql.tables.Post.POST.ID.equal(id))
                .execute();
        LOGGER.info(String.format("DEL_Group_ID_Count ==== %S",delCount));
        return deleted;
    }
    @Override
    public List<Post> findAll() {
        LOGGER.info("Finding all Org entries.");
        List<PostRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Post.POST).orderBy(com.jic.tnw.db.mysql.tables.Post.POST.SEQ.asc())
                .fetchInto(PostRecord.class);
        List<Post> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] Org entries", entries.size()));
        return entries;
    }


    @Override
    public List<Post> findByContainsName(String name) {
        List<PostRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.Post.POST)
                .where(com.jic.tnw.db.mysql.tables.Post.POST.NAME.like(name + "%"))
                .fetchInto(PostRecord.class);
        return convertQueryResultToPojos(queryResults);
    }

    @Override
    public  Post add(Post entry){
//        jooq.selectFrom(POST)
//                .orderBy(POST.ID.desc())
//                .fetchOne();
//        LOGGER.debug(String.format("POST.ID.desc============%s"));
        PostRecord postRecord = jooq.insertInto(com.jic.tnw.db.mysql.tables.Post.POST)
                .set(createPostRecord(entry))
                .returning()
                .fetchOne();
        Post returned = convertQueryResultToPojo(postRecord);

        return returned;
    }
    private PostRecord createPostRecord(Post entry) {
        PostRecord record = new PostRecord();
        record.setName(entry.getName());
        record.setGroupId(entry.getGroupId());
        record.setCode(entry.getCode());
        record.setSeq(entry.getSeq());
        record.setCreatedTime(entry.getCreatedTime());
        record.setCreatedUserId(entry.getCreatedUserId());
        return record;
    }
    @Override
    public Post findById(Integer id) {
        PostRecord result = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Post.POST)
                .where(com.jic.tnw.db.mysql.tables.Post.POST.ID.equal(id))
                .fetchOne();

        return convertQueryResultToPojo(result);
    }

    @Override
    public Post update(Post entry) {

        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Post.POST)
                .set(com.jic.tnw.db.mysql.tables.Post.POST.NAME,entry.getName())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.SEQ,entry.getSeq())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.CODE,entry.getCode())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.LAST_UPD_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.LAST_UPD_USER_ID,entry.getLastUpdUserId())
                .where(com.jic.tnw.db.mysql.tables.Post.POST.ID.equal(entry.getId()))
                .execute();
        LOGGER.debug(String.format("PostGroup update_time =====%s",updateCount));
        return findById(entry.getId());
    }

    @Override
    public Post findByCode(String code){
          PostRecord queryresult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Post.POST)
                  .where(com.jic.tnw.db.mysql.tables.Post.POST.CODE.equal(code))
                  .fetchOne();
          return convertQueryResultToPojo(queryresult);
    }
    @Override
    public Post findByName(String name){
        PostRecord queryresult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Post.POST)
                .where(com.jic.tnw.db.mysql.tables.Post.POST.NAME.equal(name))
                .fetchOne();
        return convertQueryResultToPojo(queryresult);
    }

    @Override
    public List<Post> convertQueryResultToPojos(List<PostRecord> queryResults) {

        if (queryResults == null){
            return null;
        }
        List<Post> entries = new ArrayList<>();
        Post entry;
        for (PostRecord queryResult : queryResults) {
            entry = convertQueryResultToPojo(queryResult);
            entries.add(entry);
        }
        return entries;
    }
    @Override
    public List<Post> findByGroupID(Integer group_id){

        List<PostRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Post.POST)
                .where(com.jic.tnw.db.mysql.tables.Post.POST.GROUP_ID.eq(group_id))
                .fetchInto(PostRecord.class);
        LOGGER.debug(String.format("List<PostRecord>=============%s",convertQueryResultToPojos(queryResults)));
        return convertQueryResultToPojos(queryResults);
    }
    @Override
    public Post convertQueryResultToPojo(PostRecord queryResult) {
        if (queryResult == null){
            return null;
        }
        Post post = new Post();
        post.setId(queryResult.getId());
        post.setCode(queryResult.getCode());
        post.setName(queryResult.getName());
        post.setGroupId(queryResult.getGroupId());
        post.setCreatedUserId(queryResult.getCreatedUserId());
        post.setSeq(queryResult.getSeq());
        post.setLastUpdTime(queryResult.getLastUpdTime());
        post.setCreatedTime(queryResult.getCreatedTime());
        return post;
    }
    @Override
    public Post updateNameAndCode(Post entry){

        int update = jooq.update(com.jic.tnw.db.mysql.tables.Post.POST)
                .set(com.jic.tnw.db.mysql.tables.Post.POST.NAME,entry.getName())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.CODE,entry.getCode())
                .set(com.jic.tnw.db.mysql.tables.Post.POST.LAST_UPD_USER_ID,entry.getLastUpdUserId())
                .where(com.jic.tnw.db.mysql.tables.Post.POST.ID.equal(entry.getId()))
                .execute();
        return findById(entry.getId());
    }

    @Override
    protected Table<PostRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Post.POST;
    }

    @Override
    protected Class<Post> getPojoClass() {
        return Post.class;
    }
}
