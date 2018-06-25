/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.tables.UserGroupMember;
import com.jic.tnw.db.mysql.tables.records.UserGroupMemberRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ?????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class UserGroupMemberDao extends DAOImpl<UserGroupMemberRecord, com.jic.tnw.db.mysql.tables.pojos.UserGroupMember, Integer> {

    /**
     * Create a new UserGroupMemberDao without any configuration
     */
    public UserGroupMemberDao() {
        super(UserGroupMember.USER_GROUP_MEMBER, com.jic.tnw.db.mysql.tables.pojos.UserGroupMember.class);
    }

    /**
     * Create a new UserGroupMemberDao with an attached configuration
     */
    @Autowired
    public UserGroupMemberDao(Configuration configuration) {
        super(UserGroupMember.USER_GROUP_MEMBER, com.jic.tnw.db.mysql.tables.pojos.UserGroupMember.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.UserGroupMember object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchById(Integer... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.UserGroupMember fetchOneById(Integer value) {
        return fetchOne(UserGroupMember.USER_GROUP_MEMBER.ID, value);
    }

    /**
     * Fetch records that have <code>group_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByGroupId(Integer... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.GROUP_ID, values);
    }

    /**
     * Fetch records that have <code>member_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByMemberId(Integer... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.MEMBER_ID, values);
    }

    /**
     * Fetch records that have <code>member_type IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByMemberType(String... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.MEMBER_TYPE, values);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByCreatedUserId(Integer... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>last_upd_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByLastUpdUserId(Integer... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.LAST_UPD_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_upd_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroupMember> fetchByLastUpdTime(LocalDateTime... values) {
        return fetch(UserGroupMember.USER_GROUP_MEMBER.LAST_UPD_TIME, values);
    }
}