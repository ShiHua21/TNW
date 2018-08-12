package com.jic.tnw.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by lee5hx on 2017/11/29.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TianmaMyOrders {


    @JsonProperty("city")
    private String city;

    @JsonProperty("outer_tid")
    private String outerTid;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("m_warehouse_name")
    private String mWarehouseName;

    @JsonProperty("goods_no")
    private String goodsNo;

    @JsonProperty("size1")
    private String size1;

    @JsonProperty("size2")
    private String size2;

    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    @JsonProperty("discount")
    private BigDecimal discount;

    @JsonProperty("discount_price")
    private BigDecimal discountPrice;

    @JsonProperty("post_fee")
    private BigDecimal postFee;

    @JsonProperty("pay_price")
    private BigDecimal payPrice;

    @JsonProperty("name")
    private String name;

    @JsonProperty("m_delivery")
    private String mDelivery; //下单快递

    @JsonProperty("delivery")
    private String delivery; //实发快递

    @JsonProperty("p_delivery_no")
    private String pDeliveryNo; //快递单号

    @JsonProperty("status")
    private Integer status; //状态

    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private LocalDateTime created;

    @JsonProperty("feed_back_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feedBackTime;

    @JsonProperty("trade_remark")
    private String tradeRemark;

    @JsonProperty("no_shipment_remark")
    private String noShipmentRemark;

    @JsonProperty("old_status")
    private String oldStatus;
    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOuterTid() {
        return outerTid;
    }

    public void setOuterTid(String outerTid) {
        this.outerTid = outerTid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getmWarehouseName() {
        return mWarehouseName;
    }

    public void setmWarehouseName(String mWarehouseName) {
        this.mWarehouseName = mWarehouseName;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public String getSize2() {
        return size2;
    }

    public void setSize2(String size2) {
        this.size2 = size2;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmDelivery() {
        return mDelivery;
    }

    public void setmDelivery(String mDelivery) {
        this.mDelivery = mDelivery;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getpDeliveryNo() {
        return pDeliveryNo;
    }

    public void setpDeliveryNo(String pDeliveryNo) {
        this.pDeliveryNo = pDeliveryNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(LocalDateTime feedBackTime) {
        this.feedBackTime = feedBackTime;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

    public void setTradeRemark(String tradeRemark) {
        this.tradeRemark = tradeRemark;
    }

    public String getNoShipmentRemark() {
        return noShipmentRemark;
    }

    public void setNoShipmentRemark(String noShipmentRemark) {
        this.noShipmentRemark = noShipmentRemark;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
}
