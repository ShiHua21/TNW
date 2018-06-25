/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables;


import com.jic.tnw.db.converter.LocalDateTimeConverter1;
import com.jic.tnw.db.mysql.Indexes;
import com.jic.tnw.db.mysql.Keys;
import com.jic.tnw.db.mysql.Tnw;
import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.records.WebUrlResourceRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class WebUrlResource extends TableImpl<WebUrlResourceRecord> {

    private static final long serialVersionUID = -944125724;

    /**
     * The reference instance of <code>TNW.web_url_resource</code>
     */
    public static final WebUrlResource WEB_URL_RESOURCE = new WebUrlResource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WebUrlResourceRecord> getRecordType() {
        return WebUrlResourceRecord.class;
    }

    /**
     * The column <code>TNW.web_url_resource.id</code>.
     */
    public final TableField<WebUrlResourceRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>TNW.web_url_resource.wur_code</code>.
     */
    public final TableField<WebUrlResourceRecord, String> WUR_CODE = createField("wur_code", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>TNW.web_url_resource.url</code>. ??
     */
    public final TableField<WebUrlResourceRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "??");

    /**
     * The column <code>TNW.web_url_resource.code</code>. ??
     */
    public final TableField<WebUrlResourceRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "??");

    /**
     * The column <code>TNW.web_url_resource.description</code>. ????
     */
    public final TableField<WebUrlResourceRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "????");

    /**
     * The column <code>TNW.web_url_resource.parent_id</code>. ?ID
     */
    public final TableField<WebUrlResourceRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "?ID");

    /**
     * The column <code>TNW.web_url_resource.children_num</code>. ?????
     */
    public final TableField<WebUrlResourceRecord, Integer> CHILDREN_NUM = createField("children_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "?????");

    /**
     * The column <code>TNW.web_url_resource.depth</code>. tree??
     */
    public final TableField<WebUrlResourceRecord, Integer> DEPTH = createField("depth", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "tree??");

    /**
     * The column <code>TNW.web_url_resource.seq</code>. ??
     */
    public final TableField<WebUrlResourceRecord, Integer> SEQ = createField("seq", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??");

    /**
     * The column <code>TNW.web_url_resource.type</code>. ????
     */
    public final TableField<WebUrlResourceRecord, WebUrlResourceType> TYPE = createField("type", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(com.jic.tnw.db.mysql.enums.WebUrlResourceType.class), this, "????");

    /**
     * The column <code>TNW.web_url_resource.created_time</code>. ????
     */
    public final TableField<WebUrlResourceRecord, LocalDateTime> CREATED_TIME = createField("created_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "????", new LocalDateTimeConverter1());

    /**
     * The column <code>TNW.web_url_resource.created_user_id</code>. ????ID
     */
    public final TableField<WebUrlResourceRecord, Integer> CREATED_USER_ID = createField("created_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * The column <code>TNW.web_url_resource.updated_time</code>. ????
     */
    public final TableField<WebUrlResourceRecord, LocalDateTime> UPDATED_TIME = createField("updated_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "????", new LocalDateTimeConverter1());

    /**
     * The column <code>TNW.web_url_resource.updated_user_id</code>. ????ID
     */
    public final TableField<WebUrlResourceRecord, Integer> UPDATED_USER_ID = createField("updated_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * Create a <code>TNW.web_url_resource</code> table reference
     */
    public WebUrlResource() {
        this(DSL.name("web_url_resource"), null);
    }

    /**
     * Create an aliased <code>TNW.web_url_resource</code> table reference
     */
    public WebUrlResource(String alias) {
        this(DSL.name(alias), WEB_URL_RESOURCE);
    }

    /**
     * Create an aliased <code>TNW.web_url_resource</code> table reference
     */
    public WebUrlResource(Name alias) {
        this(alias, WEB_URL_RESOURCE);
    }

    private WebUrlResource(Name alias, Table<WebUrlResourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private WebUrlResource(Name alias, Table<WebUrlResourceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "web_url???");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Tnw.TNW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.WEB_URL_RESOURCE_PRIMARY, Indexes.WEB_URL_RESOURCE_WEB_URL_RESOURCE_CODE_INDEX, Indexes.WEB_URL_RESOURCE_WEB_URL_RESOURCE_TYPE_INDEX, Indexes.WEB_URL_RESOURCE_WUR_CODE_IDX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<WebUrlResourceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_WEB_URL_RESOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WebUrlResourceRecord> getPrimaryKey() {
        return Keys.KEY_WEB_URL_RESOURCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WebUrlResourceRecord>> getKeys() {
        return Arrays.<UniqueKey<WebUrlResourceRecord>>asList(Keys.KEY_WEB_URL_RESOURCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResource as(String alias) {
        return new WebUrlResource(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResource as(Name alias) {
        return new WebUrlResource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WebUrlResource rename(String name) {
        return new WebUrlResource(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WebUrlResource rename(Name name) {
        return new WebUrlResource(name, null);
    }
}
