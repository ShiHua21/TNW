package com.jic.tnw.web.api.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lee5hx on 2017/10/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterAppStep2Resource extends ResourceSupport {

    private String country;
    private String phoneNo;
    private String vcode;


    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

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

}
