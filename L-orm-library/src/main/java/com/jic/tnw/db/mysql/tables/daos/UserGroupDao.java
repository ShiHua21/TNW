/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.tables.UserGroup;
import com.jic.tnw.db.mysql.tables.records.UserGroupRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ???
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
public class UserGroupDao extends DAOImpl<UserGroupRecord, com.jic.tnw.db.mysql.tables.pojos.UserGroup, Integer> {

    /**
     * Create a new UserGroupDao without any configuration
     */
    public UserGroupDao() {
        super(UserGroup.USER_GROUP, com.jic.tnw.db.mysql.tables.pojos.UserGroup.class);
    }

    /**
     * Create a new UserGroupDao with an attached configuration
     */
    @Autowired
    public UserGroupDao(Configuration configuration) {
        super(UserGroup.USER_GROUP, com.jic.tnw.db.mysql.tables.pojos.UserGroup.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.UserGroup object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchById(Integer... values) {
        return fetch(UserGroup.USER_GROUP.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.UserGroup fetchOneById(Integer value) {
        return fetchOne(UserGroup.USER_GROUP.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByName(String... values) {
        return fetch(UserGroup.USER_GROUP.NAME, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByDescription(String... values) {
        return fetch(UserGroup.USER_GROUP.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByCode(String... values) {
        return fetch(UserGroup.USER_GROUP.CODE, values);
    }

    /**
     * Fetch a unique record that has <code>code = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.UserGroup fetchOneByCode(String value) {
        return fetchOne(UserGroup.USER_GROUP.CODE, value);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByCreatedUserId(Integer... values) {
        return fetch(UserGroup.USER_GROUP.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(UserGroup.USER_GROUP.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>last_upd_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByLastUpdUserId(Integer... values) {
        return fetch(UserGroup.USER_GROUP.LAST_UPD_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_upd_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserGroup> fetchByLastUpdTime(LocalDateTime... values) {
        return fetch(UserGroup.USER_GROUP.LAST_UPD_TIME, values);
    }
}
