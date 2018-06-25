package com.jic.tnw.web.api.vo.response.role;

import org.springframework.hateoas.ResourceSupport;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;

public class RoleResource extends ResourceSupport {
    private Integer roleId;
    private String name;
    private String code;
    private LocalDateTime creatTime;
    private String createName;
    private List<Integer> data;
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

//    private
}
