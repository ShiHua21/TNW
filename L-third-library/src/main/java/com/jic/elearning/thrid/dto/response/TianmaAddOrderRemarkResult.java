package com.jic.elearning.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/12/23.
 */


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TianmaAddOrderRemarkResult {

    //{"success":true,"msg":""}

    //订单编号
    @JsonProperty("success")
    private Boolean success;

    //订单ID
    @JsonProperty("orderId")
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
