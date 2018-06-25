/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.tables.PostGroup;
import com.jic.tnw.db.mysql.tables.records.PostGroupRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ????
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
public class PostGroupDao extends DAOImpl<PostGroupRecord, com.jic.tnw.db.mysql.tables.pojos.PostGroup, Integer> {

    /**
     * Create a new PostGroupDao without any configuration
     */
    public PostGroupDao() {
        super(PostGroup.POST_GROUP, com.jic.tnw.db.mysql.tables.pojos.PostGroup.class);
    }

    /**
     * Create a new PostGroupDao with an attached configuration
     */
    @Autowired
    public PostGroupDao(Configuration configuration) {
        super(PostGroup.POST_GROUP, com.jic.tnw.db.mysql.tables.pojos.PostGroup.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.PostGroup object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchById(Integer... values) {
        return fetch(PostGroup.POST_GROUP.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.PostGroup fetchOneById(Integer value) {
        return fetchOne(PostGroup.POST_GROUP.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchByName(String... values) {
        return fetch(PostGroup.POST_GROUP.NAME, values);
    }

    /**
     * Fetch records that have <code>seq IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchBySeq(Integer... values) {
        return fetch(PostGroup.POST_GROUP.SEQ, values);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchByCreatedUserId(Integer... values) {
        return fetch(PostGroup.POST_GROUP.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(PostGroup.POST_GROUP.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>last_upd_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchByLastUpdUserId(Integer... values) {
        return fetch(PostGroup.POST_GROUP.LAST_UPD_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_upd_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.PostGroup> fetchByLastUpdTime(LocalDateTime... values) {
        return fetch(PostGroup.POST_GROUP.LAST_UPD_TIME, values);
    }
}