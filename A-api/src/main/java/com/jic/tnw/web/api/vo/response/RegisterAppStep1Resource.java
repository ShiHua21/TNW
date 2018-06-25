package com.jic.tnw.web.api.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lee5hx on 2017/10/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterAppStep1Resource extends ResourceSupport {

    private String country;
    private String phoneNo;
    private Integer residueTime;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getResidueTime() {
        return residueTime;
    }

    public void setResidueTime(Integer residueTime) {
        this.residueTime = residueTime;
    }
}
