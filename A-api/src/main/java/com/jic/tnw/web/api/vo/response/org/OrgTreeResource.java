package com.jic.tnw.web.api.vo.response.org;

import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee5hx on 2018/3/8.
 */


public class OrgTreeResource extends ResourceSupport {

    private Integer orgId;
    private String name;
    private Integer parentId;

    private Integer childrenNum;
    private Integer depth;
    private Integer seq;
    private String description;

    private String code;
    private String orgCode;
    private Integer createdUserId;
    private String createdUserName;

    private LocalDateTime createdTime;
    private Integer lastUpdUserId;
    private LocalDateTime lastUpdTime;
    private Integer syncId;

    private List<OrgTreeResource> child = new ArrayList<>();


    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getLastUpdUserId() {
        return lastUpdUserId;
    }

    public void setLastUpdUserId(Integer lastUpdUserId) {
        this.lastUpdUserId = lastUpdUserId;
    }

    public LocalDateTime getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(LocalDateTime lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public Integer getSyncId() {
        return syncId;
    }

    public void setSyncId(Integer syncId) {
        this.syncId = syncId;
    }

    public List<OrgTreeResource> getChild() {
        return child;
    }

    public void setChild(List<OrgTreeResource> child) {
        this.child = child;
    }


    public void addChild(OrgTreeResource resource) {
        child.add(resource);
    }


}
