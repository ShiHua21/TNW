/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.records;


import com.jic.tnw.db.mysql.tables.PostGroup;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


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
public class PostGroupRecord extends UpdatableRecordImpl<PostGroupRecord> implements Record7<Integer, String, Integer, Integer, LocalDateTime, Integer, LocalDateTime> {

    private static final long serialVersionUID = -1478996382;

    /**
     * Setter for <code>TNW.post_group.id</code>. ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>TNW.post_group.id</code>. ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>TNW.post_group.name</code>. ??????
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>TNW.post_group.name</code>. ??????
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>TNW.post_group.seq</code>. ????
     */
    public void setSeq(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>TNW.post_group.seq</code>. ????
     */
    public Integer getSeq() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>TNW.post_group.created_user_id</code>. ????ID
     */
    public void setCreatedUserId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>TNW.post_group.created_user_id</code>. ????ID
     */
    public Integer getCreatedUserId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>TNW.post_group.created_time</code>. ????
     */
    public void setCreatedTime(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>TNW.post_group.created_time</code>. ????
     */
    public LocalDateTime getCreatedTime() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>TNW.post_group.last_upd_user_id</code>. ?????
     */
    public void setLastUpdUserId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>TNW.post_group.last_upd_user_id</code>. ?????
     */
    public Integer getLastUpdUserId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>TNW.post_group.last_upd_time</code>. ??????
     */
    public void setLastUpdTime(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>TNW.post_group.last_upd_time</code>. ??????
     */
    public LocalDateTime getLastUpdTime() {
        return (LocalDateTime) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Integer, LocalDateTime, Integer, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Integer, LocalDateTime, Integer, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PostGroup.POST_GROUP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PostGroup.POST_GROUP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return PostGroup.POST_GROUP.SEQ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return PostGroup.POST_GROUP.CREATED_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field5() {
        return PostGroup.POST_GROUP.CREATED_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return PostGroup.POST_GROUP.LAST_UPD_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field7() {
        return PostGroup.POST_GROUP.LAST_UPD_TIME;
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
    public Integer component3() {
        return getSeq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component5() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getLastUpdUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component7() {
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
    public Integer value3() {
        return getSeq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value5() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getLastUpdUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value7() {
        return getLastUpdTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value3(Integer value) {
        setSeq(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value4(Integer value) {
        setCreatedUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value5(LocalDateTime value) {
        setCreatedTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value6(Integer value) {
        setLastUpdUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord value7(LocalDateTime value) {
        setLastUpdTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostGroupRecord values(Integer value1, String value2, Integer value3, Integer value4, LocalDateTime value5, Integer value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PostGroupRecord
     */
    public PostGroupRecord() {
        super(PostGroup.POST_GROUP);
    }

    /**
     * Create a detached, initialised PostGroupRecord
     */
    public PostGroupRecord(Integer id, String name, Integer seq, Integer createdUserId, LocalDateTime createdTime, Integer lastUpdUserId, LocalDateTime lastUpdTime) {
        super(PostGroup.POST_GROUP);

        set(0, id);
        set(1, name);
        set(2, seq);
        set(3, createdUserId);
        set(4, createdTime);
        set(5, lastUpdUserId);
        set(6, lastUpdTime);
    }
}
