package com.jic.elearning.thrid.service;


import com.jic.elearning.thrid.domain.AliSMSHeaders;

public interface SMSService {
    void send(AliSMSHeaders aliSMSHeaders) throws Exception;
}
