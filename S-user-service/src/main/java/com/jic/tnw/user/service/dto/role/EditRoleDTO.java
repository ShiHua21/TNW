package com.jic.tnw.user.service.dto.role;

/**
 * Created by lee5hx on 2018/4/9.
 */
public class EditRoleDTO {
    private String name;
    private String[] ids;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
