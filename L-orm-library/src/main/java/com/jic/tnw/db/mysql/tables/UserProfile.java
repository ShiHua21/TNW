/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables;


import com.jic.tnw.db.converter.SqlDate2LocalDateConverter;
import com.jic.tnw.db.mysql.Indexes;
import com.jic.tnw.db.mysql.Keys;
import com.jic.tnw.db.mysql.Tnw;
import com.jic.tnw.db.mysql.enums.UserProfileGender;
import com.jic.tnw.db.mysql.tables.records.UserProfileRecord;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class UserProfile extends TableImpl<UserProfileRecord> {

    private static final long serialVersionUID = 929907931;

    /**
     * The reference instance of <code>TNW.user_profile</code>
     */
    public static final UserProfile USER_PROFILE = new UserProfile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserProfileRecord> getRecordType() {
        return UserProfileRecord.class;
    }

    /**
     * The column <code>TNW.user_profile.id</code>. ??ID
     */
    public final TableField<UserProfileRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "??ID");

    /**
     * The column <code>TNW.user_profile.idcard</code>. ?????
     */
    public final TableField<UserProfileRecord, String> IDCARD = createField("idcard", org.jooq.impl.SQLDataType.VARCHAR(24).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "?????");

    /**
     * The column <code>TNW.user_profile.gender</code>. ??
     */
    public final TableField<UserProfileRecord, UserProfileGender> GENDER = createField("gender", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(com.jic.tnw.db.mysql.enums.UserProfileGender.class), this, "??");

    /**
     * The column <code>TNW.user_profile.iam</code>. ???
     */
    public final TableField<UserProfileRecord, String> IAM = createField("iam", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "???");

    /**
     * The column <code>TNW.user_profile.birthday</code>. ??
     */
    public final TableField<UserProfileRecord, Date> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.DATE, this, "??");

    /**
     * The column <code>TNW.user_profile.city</code>. ??
     */
    public final TableField<UserProfileRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.qq</code>. QQ
     */
    public final TableField<UserProfileRecord, String> QQ = createField("qq", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "QQ");

    /**
     * The column <code>TNW.user_profile.signature</code>. ??
     */
    public final TableField<UserProfileRecord, String> SIGNATURE = createField("signature", org.jooq.impl.SQLDataType.CLOB, this, "??");

    /**
     * The column <code>TNW.user_profile.about</code>. ????
     */
    public final TableField<UserProfileRecord, String> ABOUT = createField("about", org.jooq.impl.SQLDataType.CLOB, this, "????");

    /**
     * The column <code>TNW.user_profile.company</code>. ??
     */
    public final TableField<UserProfileRecord, String> COMPANY = createField("company", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.job</code>. ??
     */
    public final TableField<UserProfileRecord, String> JOB = createField("job", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.school</code>. ??
     */
    public final TableField<UserProfileRecord, String> SCHOOL = createField("school", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.class</code>. ??
     */
    public final TableField<UserProfileRecord, String> CLASS = createField("class", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.weibo</code>. ??
     */
    public final TableField<UserProfileRecord, String> WEIBO = createField("weibo", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.weixin</code>. ??
     */
    public final TableField<UserProfileRecord, String> WEIXIN = createField("weixin", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.is_QQ_Public</code>. QQ?????
     */
    public final TableField<UserProfileRecord, Integer> IS_QQ_PUBLIC = createField("is_QQ_Public", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "QQ?????");

    /**
     * The column <code>TNW.user_profile.is_Weixin_Public</code>. ??????
     */
    public final TableField<UserProfileRecord, Integer> IS_WEIXIN_PUBLIC = createField("is_Weixin_Public", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??????");

    /**
     * The column <code>TNW.user_profile.is_Weibo_Public</code>. ??????
     */
    public final TableField<UserProfileRecord, Integer> IS_WEIBO_PUBLIC = createField("is_Weibo_Public", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "??????");

    /**
     * The column <code>TNW.user_profile.site</code>. ??
     */
    public final TableField<UserProfileRecord, String> SITE = createField("site", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "??");

    /**
     * The column <code>TNW.user_profile.field1_int</code>.
     */
    public final TableField<UserProfileRecord, Integer> FIELD1_INT = createField("field1_int", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>TNW.user_profile.field2_int</code>.
     */
    public final TableField<UserProfileRecord, Integer> FIELD2_INT = createField("field2_int", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>TNW.user_profile.field3_int</code>.
     */
    public final TableField<UserProfileRecord, Integer> FIELD3_INT = createField("field3_int", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>TNW.user_profile.field4_int</code>.
     */
    public final TableField<UserProfileRecord, Integer> FIELD4_INT = createField("field4_int", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>TNW.user_profile.field5_int</code>.
     */
    public final TableField<UserProfileRecord, Integer> FIELD5_INT = createField("field5_int", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>TNW.user_profile.field1_date</code>.
     */
    public final TableField<UserProfileRecord, LocalDate> FIELD1_DATE = createField("field1_date", org.jooq.impl.SQLDataType.DATE, this, "", new SqlDate2LocalDateConverter());

    /**
     * The column <code>TNW.user_profile.field2_date</code>.
     */
    public final TableField<UserProfileRecord, LocalDate> FIELD2_DATE = createField("field2_date", org.jooq.impl.SQLDataType.DATE, this, "", new SqlDate2LocalDateConverter());

    /**
     * The column <code>TNW.user_profile.field3_date</code>.
     */
    public final TableField<UserProfileRecord, LocalDate> FIELD3_DATE = createField("field3_date", org.jooq.impl.SQLDataType.DATE, this, "", new SqlDate2LocalDateConverter());

    /**
     * The column <code>TNW.user_profile.field4_date</code>.
     */
    public final TableField<UserProfileRecord, LocalDate> FIELD4_DATE = createField("field4_date", org.jooq.impl.SQLDataType.DATE, this, "", new SqlDate2LocalDateConverter());

    /**
     * The column <code>TNW.user_profile.field5_date</code>.
     */
    public final TableField<UserProfileRecord, LocalDate> FIELD5_DATE = createField("field5_date", org.jooq.impl.SQLDataType.DATE, this, "", new SqlDate2LocalDateConverter());

    /**
     * The column <code>TNW.user_profile.field1_float</code>.
     */
    public final TableField<UserProfileRecord, Double> FIELD1_FLOAT = createField("field1_float", org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>TNW.user_profile.field2_float</code>.
     */
    public final TableField<UserProfileRecord, Double> FIELD2_FLOAT = createField("field2_float", org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>TNW.user_profile.field3_float</code>.
     */
    public final TableField<UserProfileRecord, Double> FIELD3_FLOAT = createField("field3_float", org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>TNW.user_profile.field4_float</code>.
     */
    public final TableField<UserProfileRecord, Double> FIELD4_FLOAT = createField("field4_float", org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>TNW.user_profile.field5_float</code>.
     */
    public final TableField<UserProfileRecord, Double> FIELD5_FLOAT = createField("field5_float", org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>TNW.user_profile.Field1_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD1_VARCHAR = createField("Field1_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field2_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD2_VARCHAR = createField("Field2_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field3_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD3_VARCHAR = createField("Field3_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field4_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD4_VARCHAR = createField("Field4_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field5_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD5_VARCHAR = createField("Field5_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field6_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD6_VARCHAR = createField("Field6_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field7_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD7_VARCHAR = createField("Field7_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field8_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD8_VARCHAR = createField("Field8_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field9_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD9_VARCHAR = createField("Field9_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field10_varchar</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD10_VARCHAR = createField("Field10_varchar", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>TNW.user_profile.Field1_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD1_TEXT = createField("Field1_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field2_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD2_TEXT = createField("Field2_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field3_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD3_TEXT = createField("Field3_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field4_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD4_TEXT = createField("Field4_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field5_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD5_TEXT = createField("Field5_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field6_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD6_TEXT = createField("Field6_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field7_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD7_TEXT = createField("Field7_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field8_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD8_TEXT = createField("Field8_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field9_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD9_TEXT = createField("Field9_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>TNW.user_profile.Field10_text</code>.
     */
    public final TableField<UserProfileRecord, String> FIELD10_TEXT = createField("Field10_text", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>TNW.user_profile</code> table reference
     */
    public UserProfile() {
        this(DSL.name("user_profile"), null);
    }

    /**
     * Create an aliased <code>TNW.user_profile</code> table reference
     */
    public UserProfile(String alias) {
        this(DSL.name(alias), USER_PROFILE);
    }

    /**
     * Create an aliased <code>TNW.user_profile</code> table reference
     */
    public UserProfile(Name alias) {
        this(alias, USER_PROFILE);
    }

    private UserProfile(Name alias, Table<UserProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserProfile(Name alias, Table<UserProfileRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.USER_PROFILE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserProfileRecord> getPrimaryKey() {
        return Keys.KEY_USER_PROFILE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<UserProfileRecord>>asList(Keys.KEY_USER_PROFILE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfile as(String alias) {
        return new UserProfile(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfile as(Name alias) {
        return new UserProfile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserProfile rename(String name) {
        return new UserProfile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserProfile rename(Name name) {
        return new UserProfile(name, null);
    }
}