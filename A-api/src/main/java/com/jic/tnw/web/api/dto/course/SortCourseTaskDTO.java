package com.jic.tnw.web.api.dto.course;

/**
 * Created by lee5hx on 2018/4/12.
 */
public class SortCourseTaskDTO {
    private Integer cid;
    private Integer sid;
    private String[] sortIds;

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

    public String[] getSortIds() {
        return sortIds;
    }

    public void setSortIds(String[] sortIds) {
        this.sortIds = sortIds;
    }
}
