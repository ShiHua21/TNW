/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.records;


import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.WebUrlResource;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


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
public class WebUrlResourceRecord extends UpdatableRecordImpl<WebUrlResourceRecord> implements Record14<Integer, String, String, String, String, Integer, Integer, Integer, Integer, WebUrlResourceType, LocalDateTime, Integer, LocalDateTime, Integer> {

    private static final long serialVersionUID = 179366899;

    /**
     * Setter for <code>TNW.web_url_resource.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>TNW.web_url_resource.wur_code</code>.
     */
    public void setWurCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.wur_code</code>.
     */
    public String getWurCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>TNW.web_url_resource.url</code>. ??
     */
    public void setUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.url</code>. ??
     */
    public String getUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>TNW.web_url_resource.code</code>. ??
     */
    public void setCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.code</code>. ??
     */
    public String getCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>TNW.web_url_resource.description</code>. ????
     */
    public void setDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.description</code>. ????
     */
    public String getDescription() {
        return (String) get(4);
    }

    /**
     * Setter for <code>TNW.web_url_resource.parent_id</code>. ?ID
     */
    public void setParentId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.parent_id</code>. ?ID
     */
    public Integer getParentId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>TNW.web_url_resource.children_num</code>. ?????
     */
    public void setChildrenNum(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.children_num</code>. ?????
     */
    public Integer getChildrenNum() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>TNW.web_url_resource.depth</code>. tree??
     */
    public void setDepth(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.depth</code>. tree??
     */
    public Integer getDepth() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>TNW.web_url_resource.seq</code>. ??
     */
    public void setSeq(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.seq</code>. ??
     */
    public Integer getSeq() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>TNW.web_url_resource.type</code>. ????
     */
    public void setType(WebUrlResourceType value) {
        set(9, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.type</code>. ????
     */
    public WebUrlResourceType getType() {
        return (WebUrlResourceType) get(9);
    }

    /**
     * Setter for <code>TNW.web_url_resource.created_time</code>. ????
     */
    public void setCreatedTime(LocalDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.created_time</code>. ????
     */
    public LocalDateTime getCreatedTime() {
        return (LocalDateTime) get(10);
    }

    /**
     * Setter for <code>TNW.web_url_resource.created_user_id</code>. ????ID
     */
    public void setCreatedUserId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.created_user_id</code>. ????ID
     */
    public Integer getCreatedUserId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>TNW.web_url_resource.updated_time</code>. ????
     */
    public void setUpdatedTime(LocalDateTime value) {
        set(12, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.updated_time</code>. ????
     */
    public LocalDateTime getUpdatedTime() {
        return (LocalDateTime) get(12);
    }

    /**
     * Setter for <code>TNW.web_url_resource.updated_user_id</code>. ????ID
     */
    public void setUpdatedUserId(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>TNW.web_url_resource.updated_user_id</code>. ????ID
     */
    public Integer getUpdatedUserId() {
        return (Integer) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, String, Integer, Integer, Integer, Integer, WebUrlResourceType, LocalDateTime, Integer, LocalDateTime, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, String, Integer, Integer, Integer, Integer, WebUrlResourceType, LocalDateTime, Integer, LocalDateTime, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return WebUrlResource.WEB_URL_RESOURCE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return WebUrlResource.WEB_URL_RESOURCE.WUR_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return WebUrlResource.WEB_URL_RESOURCE.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return WebUrlResource.WEB_URL_RESOURCE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return WebUrlResource.WEB_URL_RESOURCE.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return WebUrlResource.WEB_URL_RESOURCE.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return WebUrlResource.WEB_URL_RESOURCE.CHILDREN_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return WebUrlResource.WEB_URL_RESOURCE.DEPTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return WebUrlResource.WEB_URL_RESOURCE.SEQ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<WebUrlResourceType> field10() {
        return WebUrlResource.WEB_URL_RESOURCE.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field11() {
        return WebUrlResource.WEB_URL_RESOURCE.CREATED_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return WebUrlResource.WEB_URL_RESOURCE.CREATED_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field13() {
        return WebUrlResource.WEB_URL_RESOURCE.UPDATED_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return WebUrlResource.WEB_URL_RESOURCE.UPDATED_USER_ID;
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
        return getWurCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getChildrenNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getDepth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getSeq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceType component10() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component11() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component13() {
        return getUpdatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getUpdatedUserId();
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
        return getWurCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getChildrenNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getDepth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getSeq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceType value10() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value11() {
        return getCreatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getCreatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value13() {
        return getUpdatedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getUpdatedUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value2(String value) {
        setWurCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value3(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value4(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value5(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value6(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value7(Integer value) {
        setChildrenNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value8(Integer value) {
        setDepth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value9(Integer value) {
        setSeq(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value10(WebUrlResourceType value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value11(LocalDateTime value) {
        setCreatedTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value12(Integer value) {
        setCreatedUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value13(LocalDateTime value) {
        setUpdatedTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord value14(Integer value) {
        setUpdatedUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUrlResourceRecord values(Integer value1, String value2, String value3, String value4, String value5, Integer value6, Integer value7, Integer value8, Integer value9, WebUrlResourceType value10, LocalDateTime value11, Integer value12, LocalDateTime value13, Integer value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WebUrlResourceRecord
     */
    public WebUrlResourceRecord() {
        super(WebUrlResource.WEB_URL_RESOURCE);
    }

    /**
     * Create a detached, initialised WebUrlResourceRecord
     */
    public WebUrlResourceRecord(Integer id, String wurCode, String url, String code, String description, Integer parentId, Integer childrenNum, Integer depth, Integer seq, WebUrlResourceType type, LocalDateTime createdTime, Integer createdUserId, LocalDateTime updatedTime, Integer updatedUserId) {
        super(WebUrlResource.WEB_URL_RESOURCE);

        set(0, id);
        set(1, wurCode);
        set(2, url);
        set(3, code);
        set(4, description);
        set(5, parentId);
        set(6, childrenNum);
        set(7, depth);
        set(8, seq);
        set(9, type);
        set(10, createdTime);
        set(11, createdUserId);
        set(12, updatedTime);
        set(13, updatedUserId);
    }
}
