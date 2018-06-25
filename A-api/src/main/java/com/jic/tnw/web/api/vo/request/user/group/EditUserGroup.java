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
public class EditUserGroup {

    /**
     * name
     * user.group.name.not.blank = 机构名称不能为空!
     */
    @NotBlank(message = "{user.group.name.not.blank}")
    private String name;
    //user.org.code.not.blank = 机构编码不能为空!
    /**
     * code
     * user.group.code.not.blank = 编码不能为空!
     */
    @NotBlank(message = "{user.group.code.not.blank}")
    private String code;
    /**
     * description
     * user.group.description.not.blank = 描述不能为空!
     */
    //@NotBlank(message = "{user.group.description.not.blank}")
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddUser (");
        sb.append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(")");
        return sb.toString();
    }
}
