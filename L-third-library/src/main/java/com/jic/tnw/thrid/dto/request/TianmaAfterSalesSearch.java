package com.jic.tnw.thrid.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/11/29.
 */
public class TianmaAfterSalesSearch {

//    status:2
//    oldWareHouseId:
//    proxyOrderID:100073063600492252
//    articleno:
//    size:
//    addressee:
//    backExpressNo:
//    isBackExpressNoExist:
//    problemType:
//    pickingStartTime:
//    pickingEndsTime:
//    receiptStartTime:
//    receiptEndsTime:
//    addStartTime:
//    addEndsTime:
//    orderId:
//    page:1
//    rows:15
    @JsonProperty("status")
    private String status;


    @JsonProperty("proxyOrderID")
    private String proxyOrderID;

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

    public String getProxyOrderID() {
        return proxyOrderID;
    }

    public void setProxyOrderID(String proxyOrderID) {
        this.proxyOrderID = proxyOrderID;
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
        if(proxyOrderID!=null){
            sb.append(String.format("&proxyOrderID=%s",proxyOrderID));
        }
        if(page!=null){
            sb.append(String.format("&page=%d",page));
        }
        if(rows!=null){
            sb.append(String.format("&rows=%d",rows));
        }
        return sb.toString();
    }
}
