package com.jic.elearning.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by lee5hx on 2017/11/29.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TianmaAfterSales {



//    @JsonProperty("created")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
//    private LocalDateTime created;
//
//    @JsonProperty("feed_back_time")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime feedBackTime;

    //订单编号
    @JsonProperty("proxyOrderID")
    private String proxyOrderID;

    //订单ID
    @JsonProperty("orderId")
    private String orderId;

    //货源
    @JsonProperty("p_warehouse")
    private String pWarehouse;

    //收件人
    @JsonProperty("addressee")
    private String addressee;

    //货号
    @JsonProperty("articleno")
    private String articleno;

    //尺码
    @JsonProperty("size")
    private String size;
    @JsonProperty("size1")
    private String size1;

    //市场价
    @JsonProperty("marketPrice")
    private BigDecimal marketPrice;

    //邮费
    @JsonProperty("postage")
    private BigDecimal postage;

    //寄回单号
    @JsonProperty("backExpressNo")
    private String backExpressNo;

    //寄回快递
    @JsonProperty("backDelivery")
    private String backDelivery;

    //添加时间
    @JsonProperty("addTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;

    //问题类型
    @JsonProperty("problemType")
    private String problemType;

    //售后状态 "status": 2,
    @JsonProperty("status")
    private String status;

    //配货时间 "pickingDate": "2017-12-01 11:27:00",
    @JsonProperty("pickingDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickingDate;


    //问题内容:
    //"problemContent": "退货 12-04 13:18<br/>",
    @JsonProperty("problemContent")
    private String problemContent;


    //回复:
    //"problemReply": "代理顾客收到不合适退总仓",
    @JsonProperty("problemReply")
    private String problemReply;

    //退回地址:
    @JsonProperty("sendBackAddr")
    private String sendBackAddr;


    public String getProxyOrderID() {
        return proxyOrderID;
    }

    public void setProxyOrderID(String proxyOrderID) {
        this.proxyOrderID = proxyOrderID;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpWarehouse() {
        return pWarehouse;
    }

    public void setpWarehouse(String pWarehouse) {
        this.pWarehouse = pWarehouse;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getArticleno() {
        return articleno;
    }

    public void setArticleno(String articleno) {
        this.articleno = articleno;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public String getBackExpressNo() {
        return backExpressNo;
    }

    public void setBackExpressNo(String backExpressNo) {
        this.backExpressNo = backExpressNo;
    }

    public String getBackDelivery() {
        return backDelivery;
    }

    public void setBackDelivery(String backDelivery) {
        this.backDelivery = backDelivery;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPickingDate() {
        return pickingDate;
    }

    public void setPickingDate(LocalDateTime pickingDate) {
        this.pickingDate = pickingDate;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getProblemReply() {
        return problemReply;
    }

    public void setProblemReply(String problemReply) {
        this.problemReply = problemReply;
    }

    public String getSendBackAddr() {
        return sendBackAddr;
    }

    public void setSendBackAddr(String sendBackAddr) {
        this.sendBackAddr = sendBackAddr;
    }
}
