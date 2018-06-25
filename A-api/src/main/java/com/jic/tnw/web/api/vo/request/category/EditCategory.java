package com.jic.tnw.web.api.vo.request.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * Created by lee5hx on 2018/3/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class EditCategory {

    @NotBlank(message = "{course.category.name.not.blank}")
    private String name;
    @NotBlank(message = "{course.category.code.not.blank}")
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EditCategory (");
        sb.append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(code);
        sb.append(")");
        return sb.toString();
    }
}
