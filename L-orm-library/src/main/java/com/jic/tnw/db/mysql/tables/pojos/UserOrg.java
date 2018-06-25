/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Generated;


/**
 * ????????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserOrg implements Serializable {

    private static final long serialVersionUID = 160279594;

    private Integer       id;
    private Integer       userId;
    private Integer       orgId;
    private String        orgCode;
    private Integer       createdUserId;
    private LocalDateTime createdTime;
    private Integer       lastUpdUserId;
    private LocalDateTime lastUpdTime;

    public UserOrg() {}

    public UserOrg(UserOrg value) {
        this.id = value.id;
        this.userId = value.userId;
        this.orgId = value.orgId;
        this.orgCode = value.orgCode;
        this.createdUserId = value.createdUserId;
        this.createdTime = value.createdTime;
        this.lastUpdUserId = value.lastUpdUserId;
        this.lastUpdTime = value.lastUpdTime;
    }

    public UserOrg(
        Integer       id,
        Integer       userId,
        Integer       orgId,
        String        orgCode,
        Integer       createdUserId,
        LocalDateTime createdTime,
        Integer       lastUpdUserId,
        LocalDateTime lastUpdTime
    ) {
        this.id = id;
        this.userId = userId;
        this.orgId = orgId;
        this.orgCode = orgCode;
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

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserOrg (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(orgId);
        sb.append(", ").append(orgCode);
        sb.append(", ").append(createdUserId);
        sb.append(", ").append(createdTime);
        sb.append(", ").append(lastUpdUserId);
        sb.append(", ").append(lastUpdTime);

        sb.append(")");
        return sb.toString();
    }
}
