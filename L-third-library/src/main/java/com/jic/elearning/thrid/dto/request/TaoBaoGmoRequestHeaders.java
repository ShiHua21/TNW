package com.jic.elearning.thrid.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee5hx on 2017/12/8.
 */
public class TaoBaoGmoRequestHeaders {


    @JsonProperty("Accept")
    private String accept;
    @JsonProperty("X-Requested-With")
    private String xRequestedWith;
    @JsonProperty("User-Agent")
    private String userAgent;
    @JsonProperty("Content-Type")
    private String contentType;
    @JsonProperty("Referer")
    private String referer;
    @JsonProperty("Accept-Encoding")
    private String acceptEncoding;
    @JsonProperty("Accept-Language")
    private String acceptLanguage;
    @JsonProperty("Cookie")
    private String cookie;

    public TaoBaoGmoRequestHeaders() {

    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getxRequestedWith() {
        return xRequestedWith;
    }

    public void setxRequestedWith(String xRequestedWith) {
        this.xRequestedWith = xRequestedWith;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
