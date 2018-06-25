/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.tables.Role;
import com.jic.tnw.db.mysql.tables.records.RoleRecord;

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
public class RoleDao extends DAOImpl<RoleRecord, com.jic.tnw.db.mysql.tables.pojos.Role, Integer> {

    /**
     * Create a new RoleDao without any configuration
     */
    public RoleDao() {
        super(Role.ROLE, com.jic.tnw.db.mysql.tables.pojos.Role.class);
    }

    /**
     * Create a new RoleDao with an attached configuration
     */
    @Autowired
    public RoleDao(Configuration configuration) {
        super(Role.ROLE, com.jic.tnw.db.mysql.tables.pojos.Role.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.Role object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchById(Integer... values) {
        return fetch(Role.ROLE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.Role fetchOneById(Integer value) {
        return fetchOne(Role.ROLE.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByName(String... values) {
        return fetch(Role.ROLE.NAME, values);
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByCode(String... values) {
        return fetch(Role.ROLE.CODE, values);
    }

    /**
     * Fetch records that have <code>data IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByData(String... values) {
        return fetch(Role.ROLE.DATA, values);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByCreatedUserId(Integer... values) {
        return fetch(Role.ROLE.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(Role.ROLE.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>last_upd_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByLastUpdUserId(Integer... values) {
        return fetch(Role.ROLE.LAST_UPD_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_upd_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.Role> fetchByLastUpdTime(LocalDateTime... values) {
        return fetch(Role.ROLE.LAST_UPD_TIME, values);
    }
}
