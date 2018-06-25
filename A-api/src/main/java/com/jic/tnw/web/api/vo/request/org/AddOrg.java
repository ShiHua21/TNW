package com.jic.tnw.web.api.vo.request.org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * Created by lee5hx on 2018/3/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddOrg {

    //user.org.parentId.not.blank = 上级机构并不能为空!
    @NotBlank(message = "{user.org.parentId.not.blank}")
    private String parentId;
    //user.org.name.not.blank = 机构名称不能为空!
    @NotBlank(message = "{user.org.name.not.blank}")
    private String name;
    //user.org.code.not.blank = 机构编码不能为空!
    @NotBlank(message = "{user.org.code.not.blank}")
    private String code;
    //user.org.description.not.blank = 机构描述不能为空!
    //@NotBlank(message = "{user.org.description.not.blank}")
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("AddOrg (");
        sb.append(name);
        sb.append(", ").append(parentId);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(")");
        return sb.toString();
    }
}
