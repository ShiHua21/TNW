package com.jic.tnw.thrid.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/12/8.
 */
public class TianMaMainRequestHeaders {
//    @JsonProperty("Accept")
//    private String accept;
//    @JsonProperty("X-Requested-With")
//    private String xRequestedWith;
//    @JsonProperty("User-Agent")
//    private String userAgent;
//    @JsonProperty("Content-Type")
//    private String contentType;
//    @JsonProperty("Referer")
//    private String referer;
//    @JsonProperty("Accept-Encoding")
//    private String acceptEncoding;
//    @JsonProperty("Accept-Language")
//    private String acceptLanguage;
    @JsonProperty("Cookie")
    private String cookie;

    public TianMaMainRequestHeaders() {

    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
