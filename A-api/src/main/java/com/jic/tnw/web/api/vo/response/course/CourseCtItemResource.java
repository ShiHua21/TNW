package com.jic.tnw.web.api.vo.response.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author lee5hx
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseCtItemResource extends ResourceSupport {

    private Integer cid;
    private Integer sid;
    private String itemIdType;
    private Integer itemId;
    private String itemName;
    private String itemType;
    private Integer itemNumber;
    private Integer itemSeq;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Integer itemSeq) {
        this.itemSeq = itemSeq;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getItemIdType() {
        return itemIdType;
    }

    public void setItemIdType(String itemIdType) {
        this.itemIdType = itemIdType;
    }
}
