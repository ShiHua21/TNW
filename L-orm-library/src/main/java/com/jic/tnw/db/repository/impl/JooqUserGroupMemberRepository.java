package com.jic.tnw.db.repository.impl;


import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.tables.pojos.UserGroupMember;
import com.jic.tnw.db.mysql.tables.records.UserGroupMemberRecord;
import com.jic.tnw.db.repository.UserGroupMemberRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee5hx
 */
@Repository
public class JooqUserGroupMemberRepository extends AbstractJooqRepository<UserGroupMember, UserGroupMemberRecord> implements UserGroupMemberRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqUserGroupMemberRepository.class);

    private final DSLContext jooq;

    @Autowired
    public JooqUserGroupMemberRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserGroupMember add(UserGroupMember entry) {
        UserGroupMemberRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        UserGroupMember returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private UserGroupMemberRecord createRecord(UserGroupMember entry) {
        UserGroupMemberRecord record = new UserGroupMemberRecord();
        record.setGroupId(entry.getGroupId());
        record.setMemberId(entry.getMemberId());
        record.setMemberType(entry.getMemberType());
        record.setCreatedTime(entry.getCreatedTime());
        record.setCreatedUserId(entry.getCreatedUserId());
        return record;
    }

    @Override
    public UserGroupMember delete(Integer id) {
        UserGroupMember deleted = findById(id);
        int delCount = jooq.delete(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.ID.equal(id))
                .execute();
        LOGGER.info(String.format("User.Group.Member delete count %d", delCount));
        return deleted;
    }

    @Override
    public List<UserGroupMember> findByGroupId(Integer groupId) {
        LOGGER.info(String.format("Found UserGroupMember findByGroupId=%d", groupId));
        List<UserGroupMemberRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.GROUP_ID.eq(groupId))
                .fetchInto(UserGroupMemberRecord.class);
        List<UserGroupMember> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%s] UserGroupMember entries", entries.size()));
        return entries;
    }

    @Override
    public List<UserGroupMember> findAll() {
        return null;
    }

    @Override
    public UserGroupMember findById(Integer id) {
        UserGroupMemberRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER).where(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.ID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public UserGroupMember findByMemberIdAndType(Integer groupId,Integer memberId,UserGroupMemberMemberType memberType) {
        UserGroupMemberRecord queryResult = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER)
                .where(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.MEMBER_ID.eq(memberId))
                .and(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.MEMBER_TYPE.eq(memberType))
                .and(com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER.GROUP_ID.eq(groupId))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public UserGroupMember update(UserGroupMember entry) {
        return null;
    }

    @Override
    public UserGroupMember convertQueryResultToPojo(UserGroupMemberRecord queryResult) {
        if (queryResult == null) {
            return null;
        }
        return queryResult.into(UserGroupMember.class);
    }

    @Override
    public List<UserGroupMember> convertQueryResultToPojos(List<UserGroupMemberRecord> queryResults) {
        List<UserGroupMember> entries = new ArrayList<>();
        for (UserGroupMemberRecord queryResult : queryResults) {
            UserGroupMember entry = convertQueryResultToPojo(queryResult);
            entries.add(entry);
        }
        return entries;
    }


    @Override
    protected Table<UserGroupMemberRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER;
    }

    @Override
    protected Class<UserGroupMember> getPojoClass() {
        return UserGroupMember.class;
    }
}
