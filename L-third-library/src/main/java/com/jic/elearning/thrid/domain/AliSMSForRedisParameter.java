package com.jic.elearning.thrid.domain;

import java.util.Date;

public class AliSMSForRedisParameter {
    private String code;
    private Date createTime;
    private Boolean used;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
