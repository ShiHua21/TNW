package com.jic.tnw.thrid.service;


import com.jic.tnw.thrid.domain.VerifyCode;


public interface SMSCodeService {

    String sendCode(String countryCode,String phoneNumber,String scene) throws Exception;

    Boolean verifyCode(VerifyCode verifyCode);

    void useCode(VerifyCode verifyCode);

}
