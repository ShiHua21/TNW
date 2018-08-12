package com.jic.tnw.thrid.dto.response;

/**
 * Created by lee5hx on 2017/12/4.
 * {"logoutFlag":true,"success":false,"msg":"登录超时."}
 */

public class TianmaLogout {
    private Boolean logoutFlag;
    private Boolean success;
    private String msg;

    public Boolean getLogoutFlag() {
        return logoutFlag;
    }

    public void setLogoutFlag(Boolean logoutFlag) {
        this.logoutFlag = logoutFlag;
    }

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
