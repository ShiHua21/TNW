package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.pojos.WebUrlResource;
import com.jic.tnw.db.mysql.tables.records.WebUrlResourceRecord;
import com.jic.tnw.db.repository.WebUrlResourceRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
@Repository
public class JooqWebUrlResourceRepository extends AbstractJooqRepository<WebUrlResource, WebUrlResourceRecord> implements WebUrlResourceRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqWebUrlResourceRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqWebUrlResourceRepository(DSLContext jooq) {
        this.jooq = jooq;
    }


    @Override
    public List<WebUrlResource> findListByType(WebUrlResourceType type) {

        List<WebUrlResourceRecord> queryResults = jooq.selectFrom(com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE)
                .where(com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE.TYPE.eq(type))
                .fetchInto(WebUrlResourceRecord.class);

        LOGGER.info("WebUrlResource.queryResults.size"+queryResults.size());
        return convertQueryResultToPojos(queryResults);
    }

    @Override
    public WebUrlResource add(WebUrlResource entry) {
        return null;
    }

    @Override
    public WebUrlResource delete(Integer id) {
        return null;
    }

    @Override
    public List<WebUrlResource> findAll() {
        LOGGER.info("Finding all WebUrlResource entries.");
        List<WebUrlResourceRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE)
                .fetchInto(WebUrlResourceRecord.class);
        List<WebUrlResource> entries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%d] WebUrlResource entries", entries.size()));
        return entries;
    }

    @Override
    public WebUrlResource findById(Integer id) {
        WebUrlResourceRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE).where(com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE.ID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);    }

    @Override
    public WebUrlResource update(WebUrlResource entry) {
        return null;
    }


    @Override
    protected Table<WebUrlResourceRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE;
    }

    @Override
    protected Class<WebUrlResource> getPojoClass() {
        return WebUrlResource.class;
    }
}
