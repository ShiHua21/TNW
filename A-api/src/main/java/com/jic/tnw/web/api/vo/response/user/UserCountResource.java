package com.jic.tnw.web.api.vo.response.user;

public class UserCountResource {
    Integer lockCount;

    Long allCount;

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Integer getLockCount() {
        return lockCount;
    }

    public void setLockCount(Integer lockCount) {
        this.lockCount = lockCount;
    }
}
