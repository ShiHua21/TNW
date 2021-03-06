/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.daos;


import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.WebUrlResource;
import com.jic.tnw.db.mysql.tables.records.WebUrlResourceRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * web_url???
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
public class WebUrlResourceDao extends DAOImpl<WebUrlResourceRecord, com.jic.tnw.db.mysql.tables.pojos.WebUrlResource, Integer> {

    /**
     * Create a new WebUrlResourceDao without any configuration
     */
    public WebUrlResourceDao() {
        super(WebUrlResource.WEB_URL_RESOURCE, com.jic.tnw.db.mysql.tables.pojos.WebUrlResource.class);
    }

    /**
     * Create a new WebUrlResourceDao with an attached configuration
     */
    @Autowired
    public WebUrlResourceDao(Configuration configuration) {
        super(WebUrlResource.WEB_URL_RESOURCE, com.jic.tnw.db.mysql.tables.pojos.WebUrlResource.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.jic.tnw.db.mysql.tables.pojos.WebUrlResource object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchById(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.jic.tnw.db.mysql.tables.pojos.WebUrlResource fetchOneById(Integer value) {
        return fetchOne(WebUrlResource.WEB_URL_RESOURCE.ID, value);
    }

    /**
     * Fetch records that have <code>wur_code IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByWurCode(String... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.WUR_CODE, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByUrl(String... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.URL, values);
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByCode(String... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.CODE, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByDescription(String... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>parent_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByParentId(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.PARENT_ID, values);
    }

    /**
     * Fetch records that have <code>children_num IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByChildrenNum(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.CHILDREN_NUM, values);
    }

    /**
     * Fetch records that have <code>depth IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByDepth(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.DEPTH, values);
    }

    /**
     * Fetch records that have <code>seq IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchBySeq(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.SEQ, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByType(WebUrlResourceType... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.TYPE, values);
    }

    /**
     * Fetch records that have <code>created_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByCreatedTime(LocalDateTime... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.CREATED_TIME, values);
    }

    /**
     * Fetch records that have <code>created_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByCreatedUserId(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.CREATED_USER_ID, values);
    }

    /**
     * Fetch records that have <code>updated_time IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByUpdatedTime(LocalDateTime... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.UPDATED_TIME, values);
    }

    /**
     * Fetch records that have <code>updated_user_id IN (values)</code>
     */
    public List<com.jic.tnw.db.mysql.tables.pojos.WebUrlResource> fetchByUpdatedUserId(Integer... values) {
        return fetch(WebUrlResource.WEB_URL_RESOURCE.UPDATED_USER_ID, values);
    }
}
