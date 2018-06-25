package com.jic.tnw.web.api.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lee5hx on 2017/10/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterAppStep3Resource extends ResourceSupport {

    private String phoneNo;
    private String userName;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
