package com.jic.tnw.user.service.dto.role;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lee5hx on 2018/4/9.
 */
public class AddRoleDTO {
    private String name;
    private String code;
    private String[] ids;
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

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
