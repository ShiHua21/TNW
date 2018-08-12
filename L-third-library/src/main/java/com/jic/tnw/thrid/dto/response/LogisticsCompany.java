package com.jic.tnw.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/12/9.
 */
public class LogisticsCompany {


    //cpCode":"POST","cpName":"中国邮政




    @JsonProperty("cpCode")
    private String cpCode;
    @JsonProperty("cpName")
    private String cpName;

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }
}
