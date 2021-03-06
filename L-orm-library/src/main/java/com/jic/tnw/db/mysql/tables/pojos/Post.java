/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Generated;


/**
 * ??
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Post implements Serializable {

    private static final long serialVersionUID = 1409054883;

    private Integer       id;
    private String        name;
    private Integer       groupId;
    private Integer       seq;
    private String        code;
    private Integer       createdUserId;
    private LocalDateTime createdTime;
    private Integer       lastUpdUserId;
    private LocalDateTime lastUpdTime;

    public Post() {}

    public Post(Post value) {
        this.id = value.id;
        this.name = value.name;
        this.groupId = value.groupId;
        this.seq = value.seq;
        this.code = value.code;
        this.createdUserId = value.createdUserId;
        this.createdTime = value.createdTime;
        this.lastUpdUserId = value.lastUpdUserId;
        this.lastUpdTime = value.lastUpdTime;
    }

    public Post(
        Integer       id,
        String        name,
        Integer       groupId,
        Integer       seq,
        String        code,
        Integer       createdUserId,
        LocalDateTime createdTime,
        Integer       lastUpdUserId,
        LocalDateTime lastUpdTime
    ) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.seq = seq;
        this.code = code;
        this.createdUserId = createdUserId;
        this.createdTime = createdTime;
        this.lastUpdUserId = lastUpdUserId;
        this.lastUpdTime = lastUpdTime;
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

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Post (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(groupId);
        sb.append(", ").append(seq);
        sb.append(", ").append(code);
        sb.append(", ").append(createdUserId);
        sb.append(", ").append(createdTime);
        sb.append(", ").append(lastUpdUserId);
        sb.append(", ").append(lastUpdTime);

        sb.append(")");
        return sb.toString();
    }
}
