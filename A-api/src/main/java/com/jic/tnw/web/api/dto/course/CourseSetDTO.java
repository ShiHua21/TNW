package com.jic.tnw.web.api.dto.course;

import java.time.LocalDateTime;

public class CourseSetDTO {
    private Boolean enableFinish;
    private Boolean buyAble;
    private Integer cid;
    private Integer sid;
//    private Boolean tryLookAble;
//    private Float maxCoursePrice;
//    private Float minCoursePrice;
//    private Float coinPrice;
//    private Float originPrice;

    public Boolean getEnableFinish() {
        return enableFinish;
    }

    public void setEnableFinish(Boolean enableFinish) {
        this.enableFinish = enableFinish;
    }

    public Boolean getBuyAble() {
        return buyAble;
    }

    public void setBuyAble(Boolean buyAble) {
        this.buyAble = buyAble;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
}
