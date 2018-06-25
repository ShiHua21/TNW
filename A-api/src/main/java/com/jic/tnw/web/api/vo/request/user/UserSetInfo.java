package com.jic.tnw.web.api.vo.request.user;

import com.jic.tnw.db.mysql.enums.UserProfileGender;
import org.springframework.core.convert.converter.Converter;

public class UserSetInfo {

    private String idcard;
    private UserProfileGender gender;
    private String qq;
    private String signature;
    private String about;
    private String weibo;
    private String weixin;
    private String mobile;
    private String site;
    private String iam;
    private String turename;

    public String getTurename() {
        return turename;
    }

    public void setTurename(String turename) {
        this.turename = turename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public UserProfileGender getGender() {
        return gender;
    }

    public void setGender(UserProfileGender gender) {
        this.gender = gender;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIam() {
        return iam;
    }

    public void setIam(String iam) {
        this.iam = iam;
    }

    public <T> T toDTO(Converter<UserSetInfo,T> converter){
        return converter.convert(this);
    }
}
