package com.jic.elearning.thrid.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/11/29.
 */
public class TianmaMyOrdersSearch {

    //    status:
    //    m_warehouse_name:
    //    goods_no:
    //    name:
    //    startTime:2017-11-14
    //    endsTime:
    //    order_id:
    //    outer_tid:98420833502963864x
    //    size1:
    //    p_delivery_no:
    //    startFeedBackTime:
    //    endsFeedBackTime:
    //    page:1
    //    rows:15
    //    names:
    //    size:
    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("m_warehouse_name")
    private String mwarehouseName;

    @JsonProperty("outer_tid")
    private String outerTid;

    @JsonProperty("page")
    private Long page;

    @JsonProperty("rows")
    private Long rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMwarehouseName() {
        return mwarehouseName;
    }

    public void setMwarehouseName(String mwarehouseName) {
        this.mwarehouseName = mwarehouseName;
    }

    public String getOuterTid() {
        return outerTid;
    }

    public void setOuterTid(String outerTid) {
        this.outerTid = outerTid;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getRows() {
        return rows;
    }

    public void setRows(Long rows) {
        this.rows = rows;
    }


    public String toFormString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size=");
        if(status!=null){
            sb.append(String.format("&status=%s",status));
        }
        if(mwarehouseName!=null){
            sb.append(String.format("&m_warehouse_name=%s",mwarehouseName));
        }
        if(outerTid!=null){
            sb.append(String.format("&outer_tid=%s",outerTid));
        }
        if(page!=null){
            sb.append(String.format("&page=%d",page));
        }
        if(rows!=null){
            sb.append(String.format("&rows=%d",rows));
        }
        if(startTime!=null){
            sb.append(String.format("&startTime=%s",startTime));
        }

        return sb.toString();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
