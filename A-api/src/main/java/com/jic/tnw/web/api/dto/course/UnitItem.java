package com.jic.tnw.web.api.dto.course;

/**
 * @author lee5hx
 */
public class UnitItem implements CtItem {

    private Integer seq;
    private Integer id;
    private Integer number;
    private String name;
    private Integer sid;
    private Integer cid;

    @Override
    public Integer getSeq() {
        return seq;
    }

    @Override
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getSid() {
        return sid;
    }

    @Override
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Override
    public Integer getCid() {
        return cid;
    }

    @Override
    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
