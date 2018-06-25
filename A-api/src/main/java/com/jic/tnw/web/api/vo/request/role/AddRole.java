package com.jic.tnw.web.api.vo.request.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.xml.soap.Text;
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class AddRole {
    @NotBlank(message = "{user.role.name.not.blank}")
    private String name;
    @NotBlank(message = "{user.role.code.not.blank}")
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
