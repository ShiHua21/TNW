package com.jic.tnw.user.service.dto;

import com.jic.tnw.db.mysql.enums.WebUrlResourceType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lee5hx on 2018/3/26.
 */
public class WurTree {

    private Integer            id;
    private String             wurCode;
    private String             url;
    private String             code;
    private String             description;
    private Integer            parentId;
    private Integer            childrenNum;
    private Integer            depth;
    private Integer            seq;
    private WebUrlResourceType type;
    private LocalDateTime createdTime;
    private Integer            createdUserId;
    private LocalDateTime      updatedTime;
    private Integer            updatedUserId;

    private List<WurTree> child;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWurCode() {
        return wurCode;
    }

    public void setWurCode(String wurCode) {
        this.wurCode = wurCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public WebUrlResourceType getType() {
        return type;
    }

    public void setType(WebUrlResourceType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public List<WurTree> getChild() {
        return child;
    }

    public void setChild(List<WurTree> child) {
        this.child = child;
    }
}
