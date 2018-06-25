/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.tables.UserOrg;
import com.jic.tnw.db.mysql.tables.records.UserOrgRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ????????
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
public class UserOrgDao extends DAOImpl<UserOrgRecord, com.jic.tnw.db.mysql.tables.pojos.UserOrg, Integer> {

    /**
     * Create a new UserOrgDao without any configuration
     */
    public UserOrgDao() {
        super(UserOrg.USER_ORG, com.jic.tnw.db.mysql.tables.pojos.UserOrg.class);
    }

    /**
     * Create a new UserOrgDao with an attached configuration
     */
    @Autowired
    public UserOrgDao(Configuration configuration) {
        super(UserOrg.USER_ORG, com.jic.tnw.db.mysql.tables.pojos.UserOrg.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.UserOrg object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchById(Integer... values) {
        return fetch(UserOrg.USER_ORG.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.UserOrg fetchOneById(Integer value) {
        return fetchOne(UserOrg.USER_ORG.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByUserId(Integer... values) {
        return fetch(UserOrg.USER_ORG.USER_ID, values);
    }

    /**
     * Fetch records that have <code>org_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByOrgId(Integer... values) {
        return fetch(UserOrg.USER_ORG.ORG_ID, values);
    }

    /**
     * Fetch records that have <code>org_code IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByOrgCode(String... values) {
        return fetch(UserOrg.USER_ORG.ORG_CODE, values);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByCreatedUserId(Integer... values) {
        return fetch(UserOrg.USER_ORG.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(UserOrg.USER_ORG.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>last_upd_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByLastUpdUserId(Integer... values) {
        return fetch(UserOrg.USER_ORG.LAST_UPD_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_upd_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.UserOrg> fetchByLastUpdTime(LocalDateTime... values) {
        return fetch(UserOrg.USER_ORG.LAST_UPD_TIME, values);
    }
}