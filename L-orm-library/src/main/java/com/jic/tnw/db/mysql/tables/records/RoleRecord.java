/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.records;


import com.jic.tnw.db.mysql.tables.Role;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


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
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> implements Record8<Integer, String, String, String, Integer, LocalDateTime, Integer, LocalDateTime> {

    private static final long serialVersionUID = -1258747162;

    /**
     * Setter for <code>TNW.role.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>TNW.role.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>TNW.role.name</code>. ????
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>TNW.role.name</code>. ????
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>TNW.role.code</code>. ????
     */
    public void setCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>TNW.role.code</code>. ????
     */
    public String getCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>TNW.role.data</code>. ????(json??)
     */
    public void setData(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>TNW.role.data</code>. ????(json??)
     */
    public String getData() {
        return (String) get(3);
    }

    /**
     * Setter for <code>TNW.role.created_user_id</code>. ????ID
     */
    public void setCreatedUserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>TNW.role.created_user_id</code>. ????ID
     */
    public Integer getCreatedUserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>TNW.role.created_time</code>. ????
     */
    public void setCreatedTime(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>TNW.role.created_time</code>. ????
     */
    public LocalDateTime getCreatedTime() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>TNW.role.last_upd_user_id</code>. ?????
     */
    public void setLastUpdUserId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>TNW.role.last_upd_user_id</code>. ?????
     */
    public Integer getLastUpdUserId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>TNW.role.last_upd_time</code>. ??????
     */
    public void setLastUpdTime(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>TNW.role.last_upd_time</code>. ??????
     */
    public LocalDateTime getLastUpdTime() {
        return (LocalDateTime) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, String, String, Integer, LocalDateTime, Integer, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, String, String, Integer, LocalDateTime, Integer, LocalDateTime> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Role.ROLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Role.ROLE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Role.ROLE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Role.ROLE.DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Role.ROLE.CREATED_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field6() {
        return Role.ROLE.CREATED_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Role.ROLE.LAST_UPD_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field8() {
        return Role.ROLE.LAST_UPD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component6() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getLastUpdUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component8() {
        return getLastUpdTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value6() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getLastUpdUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value8() {
        return getLastUpdTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value3(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value4(String value) {
        setData(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value5(Integer value) {
        setCreatedUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value6(LocalDateTime value) {
        setCreatedTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value7(Integer value) {
        setLastUpdUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value8(LocalDateTime value) {
        setLastUpdTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord values(Integer value1, String value2, String value3, String value4, Integer value5, LocalDateTime value6, Integer value7, LocalDateTime value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(Role.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(Integer id, String name, String code, String data, Integer createdUserId, LocalDateTime createdTime, Integer lastUpdUserId, LocalDateTime lastUpdTime) {
        super(Role.ROLE);

        set(0, id);
        set(1, name);
        set(2, code);
        set(3, data);
        set(4, createdUserId);
        set(5, createdTime);
        set(6, lastUpdUserId);
        set(7, lastUpdTime);
    }
}