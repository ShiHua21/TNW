package com.jic.tnw.web.api.vo.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;

/**
 * 新增用户
 * @author lee5hx
 * @date 2018/03/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class SetPromotedSeq {

    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
