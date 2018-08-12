package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.PostMember;
import com.jic.tnw.db.mysql.tables.records.PostMemberRecord;
import com.jic.tnw.db.repository.PostMemberRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JooqPostMemberRepository extends AbstractJooqRepository<PostMember,PostMemberRecord> implements PostMemberRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqOrgRepository.class);
    private final DSLContext jooq;
    @Autowired
    public JooqPostMemberRepository(DSLContext jooq){
        this.jooq = jooq;
    }
    @Override
    public PostMember delete(String id) {
        LOGGER.info(String.format("Deleting POST_MEMBER entry by id: [%d]", id));
        PostMember deleted = findById(id);
        int iid = Integer.parseInt(id);
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER.USER_ID.equal(iid))
                .execute();
        LOGGER.debug(String.format("Deleted [%d] todo entries", deletedRecordCount));
        LOGGER.info(String.format("Returning deleted POST_MEMBER entry:%s ", deleted));
        return deleted;
    }
    @Override
    public List<PostMember> findAll(){return null;}
    @Override
    public PostMember update(PostMember entry){
        return null;
    }
    @Override
    public PostMember findById(String id) {
        int iid = Integer.parseInt(id);
        PostMemberRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER).where(com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER.ID.eq(iid))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);

    }
    @Override
    public PostMember add(PostMember entry){
        PostMemberRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        PostMember returned = convertQueryResultToPojo(persisted);
        return returned;
    }
    private PostMemberRecord createRecord(PostMember entry) {
        PostMemberRecord postMemberRecord = new PostMemberRecord();
        postMemberRecord.setId(entry.getId());
        postMemberRecord.setPositionId(entry.getPositionId());
        postMemberRecord.setUserId(entry.getUserId());
        postMemberRecord.setCreatedUserId(entry.getCreatedUserId());
        postMemberRecord.setLastUpdTime(entry.getLastUpdTime());
        postMemberRecord.setCreatedTime(entry.getCreatedTime());
        postMemberRecord.setLastUpdUserId(entry.getLastUpdUserId());
        return postMemberRecord;
    }

    @Override
    protected Table<PostMemberRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER;
    }

    @Override
    protected Class<PostMember> getPojoClass() {
        return PostMember.class;
    }
}
