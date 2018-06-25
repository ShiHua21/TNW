package com.jic.tnw.web.api.vo.request.org;

/**
 * Created by lee5hx on 2018/3/5.
 */
public class EditOrg {
    private String name;
    private String code;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder("EditOrg (");
        sb.append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(")");
        return sb.toString();
    }
}
