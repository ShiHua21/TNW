/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables;


import com.jic.tnw.db.converter.LocalDateTimeConverter1;
import com.jic.tnw.db.mysql.Indexes;
import com.jic.tnw.db.mysql.Keys;
import com.jic.tnw.db.mysql.Tnw;
import com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType;
import com.jic.tnw.db.mysql.tables.records.UserGroupMemberRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


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
public class UserGroupMember extends TableImpl<UserGroupMemberRecord> {

    private static final long serialVersionUID = -1435331692;

    /**
     * The reference instance of <code>TNW.user_group_member</code>
     */
    public static final UserGroupMember USER_GROUP_MEMBER = new UserGroupMember();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserGroupMemberRecord> getRecordType() {
        return UserGroupMemberRecord.class;
    }

    /**
     * The column <code>TNW.user_group_member.id</code>. ID
     */
    public final TableField<UserGroupMemberRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "ID");

    /**
     * The column <code>TNW.user_group_member.group_id</code>. ???ID
     */
    public final TableField<UserGroupMemberRecord, Integer> GROUP_ID = createField("group_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "???ID");

    /**
     * The column <code>TNW.user_group_member.member_id</code>. ????ID
     */
    public final TableField<UserGroupMemberRecord, Integer> MEMBER_ID = createField("member_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * The column <code>TNW.user_group_member.member_type</code>. ????
     */
    public final TableField<UserGroupMemberRecord, UserGroupMemberMemberType> MEMBER_TYPE = createField("member_type", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(com.jic.tnw.db.mysql.enums.UserGroupMemberMemberType.class), this, "????");

    /**
     * The column <code>TNW.user_group_member.created_user_id</code>. ????ID
     */
    public final TableField<UserGroupMemberRecord, Integer> CREATED_USER_ID = createField("created_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * The column <code>TNW.user_group_member.created_time</code>. ????
     */
    public final TableField<UserGroupMemberRecord, LocalDateTime> CREATED_TIME = createField("created_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "????", new LocalDateTimeConverter1());

    /**
     * The column <code>TNW.user_group_member.last_upd_user_id</code>. ?????
     */
    public final TableField<UserGroupMemberRecord, Integer> LAST_UPD_USER_ID = createField("last_upd_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "?????");

    /**
     * The column <code>TNW.user_group_member.last_upd_time</code>. ??????
     */
    public final TableField<UserGroupMemberRecord, LocalDateTime> LAST_UPD_TIME = createField("last_upd_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??????", new LocalDateTimeConverter1());

    /**
     * Create a <code>TNW.user_group_member</code> table reference
     */
    public UserGroupMember() {
        this(DSL.name("user_group_member"), null);
    }

    /**
     * Create an aliased <code>TNW.user_group_member</code> table reference
     */
    public UserGroupMember(String alias) {
        this(DSL.name(alias), USER_GROUP_MEMBER);
    }

    /**
     * Create an aliased <code>TNW.user_group_member</code> table reference
     */
    public UserGroupMember(Name alias) {
        this(alias, USER_GROUP_MEMBER);
    }

    private UserGroupMember(Name alias, Table<UserGroupMemberRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserGroupMember(Name alias, Table<UserGroupMemberRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "?????");
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
        return Arrays.<Index>asList(Indexes.USER_GROUP_MEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserGroupMemberRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_GROUP_MEMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserGroupMemberRecord> getPrimaryKey() {
        return Keys.KEY_USER_GROUP_MEMBER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserGroupMemberRecord>> getKeys() {
        return Arrays.<UniqueKey<UserGroupMemberRecord>>asList(Keys.KEY_USER_GROUP_MEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMember as(String alias) {
        return new UserGroupMember(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMember as(Name alias) {
        return new UserGroupMember(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroupMember rename(String name) {
        return new UserGroupMember(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroupMember rename(Name name) {
        return new UserGroupMember(name, null);
    }
}
