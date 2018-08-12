package com.jic.tnw.thrid.service;


import com.jic.tnw.thrid.domain.AliSMSHeaders;

public interface SMSService {
    void send(AliSMSHeaders aliSMSHeaders) throws Exception;
}
