package com.jic.tnw.web.api.vo.request.user.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * 新增用户组
 * @author lee5hx
 * @date 2018/03/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddUserGroup {

    @NotBlank(message = "{user.group.name.not.blank}")
    private String name;

    @NotBlank(message = "{user.group.code.not.blank}")
    private String code;

    private String description;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddUserGroup (");
        sb.append(name);
        sb.append(", ").append(code);
        sb.append(", ").append(description);
        sb.append(")");
        return sb.toString();
    }
}
