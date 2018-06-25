package com.jic.tnw.web.api.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class RegisterAppStep1 {
    @NotBlank(message = "{auth.register.app.step.phoneNo.not.blank}")
    private String phoneNo;
    @NotBlank(message = "{auth.register.app.step.country.not.blank}")
    private String country;

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
