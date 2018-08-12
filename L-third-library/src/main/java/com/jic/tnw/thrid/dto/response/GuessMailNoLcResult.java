package com.jic.tnw.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lee5hx on 2017/12/10.
 */
public class GuessMailNoLcResult {
    //    //{"message":null,"data":[{"cpCode":"OTHER","cpName":"其他"},{"cpCode":"SF","cpName":"顺丰速运"},{"cpCode":"POST","cpName":"中国邮政"},{"cpCode":"","cpName":"请选择物流公司"}],"success":true}
    //{"message":null,"data":[{"cpCode":"POST","cpName":"中国邮政"},{"cpCode":"OTHER","cpName":"其他"},{"cpCode":"HOAU","cpName":"天地华宇"},{"cpCode":"","cpName":"请选择物流公司"}],"success":true}


    private String message;
    @JsonProperty("data")
    private List<LogisticsCompany> data;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LogisticsCompany> getData() {
        return data;
    }

    public void setData(List<LogisticsCompany> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}