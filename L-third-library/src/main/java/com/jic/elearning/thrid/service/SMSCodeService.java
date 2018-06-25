package com.jic.elearning.thrid.service;


import com.jic.elearning.thrid.domain.VerifyCode;


public interface SMSCodeService {

    Integer sendCode(String countryCode,String phoneNumber,String scene) throws Exception;

    Boolean verifyCode(VerifyCode verifyCode);

    void useCode(VerifyCode verifyCode);

}
