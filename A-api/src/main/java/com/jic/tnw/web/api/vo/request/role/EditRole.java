package com.jic.tnw.web.api.vo.request.role;

public class EditRole {
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
