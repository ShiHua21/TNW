/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables;


import com.jic.tnw.db.converter.LocalDateTimeConverter1;
import com.jic.tnw.db.mysql.Indexes;
import com.jic.tnw.db.mysql.Keys;
import com.jic.tnw.db.mysql.Tnw;
import com.jic.tnw.db.mysql.tables.records.RoleRecord;

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
public class Role extends TableImpl<RoleRecord> {

    private static final long serialVersionUID = -1494147104;

    /**
     * The reference instance of <code>TNW.role</code>
     */
    public static final Role ROLE = new Role();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RoleRecord> getRecordType() {
        return RoleRecord.class;
    }

    /**
     * The column <code>TNW.role.id</code>.
     */
    public final TableField<RoleRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>TNW.role.name</code>. ????
     */
    public final TableField<RoleRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "????");

    /**
     * The column <code>TNW.role.code</code>. ????
     */
    public final TableField<RoleRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "????");

    /**
     * The column <code>TNW.role.data</code>. ????(json??)
     */
    public final TableField<RoleRecord, String> DATA = createField("data", org.jooq.impl.SQLDataType.CLOB, this, "????(json??)");

    /**
     * The column <code>TNW.role.created_user_id</code>. ????ID
     */
    public final TableField<RoleRecord, Integer> CREATED_USER_ID = createField("created_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * The column <code>TNW.role.created_time</code>. ????
     */
    public final TableField<RoleRecord, LocalDateTime> CREATED_TIME = createField("created_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "????", new LocalDateTimeConverter1());

    /**
     * The column <code>TNW.role.last_upd_user_id</code>. ?????
     */
    public final TableField<RoleRecord, Integer> LAST_UPD_USER_ID = createField("last_upd_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "?????");

    /**
     * The column <code>TNW.role.last_upd_time</code>. ??????
     */
    public final TableField<RoleRecord, LocalDateTime> LAST_UPD_TIME = createField("last_upd_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??????", new LocalDateTimeConverter1());

    /**
     * Create a <code>TNW.role</code> table reference
     */
    public Role() {
        this(DSL.name("role"), null);
    }

    /**
     * Create an aliased <code>TNW.role</code> table reference
     */
    public Role(String alias) {
        this(DSL.name(alias), ROLE);
    }

    /**
     * Create an aliased <code>TNW.role</code> table reference
     */
    public Role(Name alias) {
        this(alias, ROLE);
    }

    private Role(Name alias, Table<RoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private Role(Name alias, Table<RoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "???");
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
        return Arrays.<Index>asList(Indexes.ROLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RoleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RoleRecord> getPrimaryKey() {
        return Keys.KEY_ROLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RoleRecord>> getKeys() {
        return Arrays.<UniqueKey<RoleRecord>>asList(Keys.KEY_ROLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role as(String alias) {
        return new Role(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role as(Name alias) {
        return new Role(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Role rename(String name) {
        return new Role(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Role rename(Name name) {
        return new Role(name, null);
    }
}
