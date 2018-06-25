/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables;


import com.jic.tnw.db.converter.LocalDateTimeConverter1;
import com.jic.tnw.db.mysql.Indexes;
import com.jic.tnw.db.mysql.Keys;
import com.jic.tnw.db.mysql.Tnw;
import com.jic.tnw.db.mysql.tables.records.PostMemberRecord;

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
public class PostMember extends TableImpl<PostMemberRecord> {

    private static final long serialVersionUID = 1036074896;

    /**
     * The reference instance of <code>TNW.post_member</code>
     */
    public static final PostMember POST_MEMBER = new PostMember();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostMemberRecord> getRecordType() {
        return PostMemberRecord.class;
    }

    /**
     * The column <code>TNW.post_member.id</code>. ID
     */
    public final TableField<PostMemberRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "ID");

    /**
     * The column <code>TNW.post_member.post_id</code>. ??ID
     */
    public final TableField<PostMemberRecord, Integer> POST_ID = createField("post_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??ID");

    /**
     * The column <code>TNW.post_member.user_id</code>. ??ID
     */
    public final TableField<PostMemberRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "??ID");

    /**
     * The column <code>TNW.post_member.created_user_id</code>. ????ID
     */
    public final TableField<PostMemberRecord, Integer> CREATED_USER_ID = createField("created_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "????ID");

    /**
     * The column <code>TNW.post_member.created_time</code>. ????
     */
    public final TableField<PostMemberRecord, LocalDateTime> CREATED_TIME = createField("created_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "????", new LocalDateTimeConverter1());

    /**
     * The column <code>TNW.post_member.last_upd_user_id</code>. ?????
     */
    public final TableField<PostMemberRecord, Integer> LAST_UPD_USER_ID = createField("last_upd_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "?????");

    /**
     * The column <code>TNW.post_member.last_upd_time</code>. ??????
     */
    public final TableField<PostMemberRecord, LocalDateTime> LAST_UPD_TIME = createField("last_upd_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??????", new LocalDateTimeConverter1());

    /**
     * Create a <code>TNW.post_member</code> table reference
     */
    public PostMember() {
        this(DSL.name("post_member"), null);
    }

    /**
     * Create an aliased <code>TNW.post_member</code> table reference
     */
    public PostMember(String alias) {
        this(DSL.name(alias), POST_MEMBER);
    }

    /**
     * Create an aliased <code>TNW.post_member</code> table reference
     */
    public PostMember(Name alias) {
        this(alias, POST_MEMBER);
    }

    private PostMember(Name alias, Table<PostMemberRecord> aliased) {
        this(alias, aliased, null);
    }

    private PostMember(Name alias, Table<PostMemberRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "????");
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
        return Arrays.<Index>asList(Indexes.POST_MEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PostMemberRecord, Integer> getIdentity() {
        return Keys.IDENTITY_POST_MEMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PostMemberRecord> getPrimaryKey() {
        return Keys.KEY_POST_MEMBER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PostMemberRecord>> getKeys() {
        return Arrays.<UniqueKey<PostMemberRecord>>asList(Keys.KEY_POST_MEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostMember as(String alias) {
        return new PostMember(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostMember as(Name alias) {
        return new PostMember(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PostMember rename(String name) {
        return new PostMember(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PostMember rename(Name name) {
        return new PostMember(name, null);
    }
}