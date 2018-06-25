/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Generated;


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
public class Org implements Serializable {

    private static final long serialVersionUID = 843541215;

    private Integer       id;
    private String        name;
    private Integer       parentId;
    private Integer       childrenNum;
    private Integer       depth;
    private Integer       seq;
    private String        description;
    private String        code;
    private String        orgCode;
    private Integer       createdUserId;
    private LocalDateTime createdTime;
    private Integer       lastUpdUserId;
    private LocalDateTime lastUpdTime;
    private Integer       syncId;

    public Org() {}

    public Org(Org value) {
        this.id = value.id;
        this.name = value.name;
        this.parentId = value.parentId;
        this.childrenNum = value.childrenNum;
        this.depth = value.depth;
        this.seq = value.seq;
        this.description = value.description;
        this.code = value.code;
        this.orgCode = value.orgCode;
        this.createdUserId = value.createdUserId;
        this.createdTime = value.createdTime;
        this.lastUpdUserId = value.lastUpdUserId;
        this.lastUpdTime = value.lastUpdTime;
        this.syncId = value.syncId;
    }

    public Org(
        Integer       id,
        String        name,
        Integer       parentId,
        Integer       childrenNum,
        Integer       depth,
        Integer       seq,
        String        description,
        String        code,
        String        orgCode,
        Integer       createdUserId,
        LocalDateTime createdTime,
        Integer       lastUpdUserId,
        LocalDateTime lastUpdTime,
        Integer       syncId
    ) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.childrenNum = childrenNum;
        this.depth = depth;
        this.seq = seq;
        this.description = description;
        this.code = code;
        this.orgCode = orgCode;
        this.createdUserId = createdUserId;
        this.createdTime = createdTime;
        this.lastUpdUserId = lastUpdUserId;
        this.lastUpdTime = lastUpdTime;
        this.syncId = syncId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getChildrenNum() {
        return this.childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getCreatedUserId() {
        return this.createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getLastUpdUserId() {
        return this.lastUpdUserId;
    }

    public void setLastUpdUserId(Integer lastUpdUserId) {
        this.lastUpdUserId = lastUpdUserId;
    }

    public LocalDateTime getLastUpdTime() {
        return this.lastUpdTime;
    }

    public void setLastUpdTime(LocalDateTime lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public Integer getSyncId() {
        return this.syncId;
    }

    public void setSyncId(Integer syncId) {
        this.syncId = syncId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Org (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(parentId);
        sb.append(", ").append(childrenNum);
        sb.append(", ").append(depth);
        sb.append(", ").append(seq);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(", ").append(orgCode);
        sb.append(", ").append(createdUserId);
        sb.append(", ").append(createdTime);
        sb.append(", ").append(lastUpdUserId);
        sb.append(", ").append(lastUpdTime);
        sb.append(", ").append(syncId);

        sb.append(")");
        return sb.toString();
    }
}
