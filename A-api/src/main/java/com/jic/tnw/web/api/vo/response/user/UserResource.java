package com.jic.tnw.web.api.vo.response.user;

import com.jic.tnw.db.mysql.enums.UserProfileGender;
import org.springframework.hateoas.ResourceSupport;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by lee5hx on 2018/3/12.
 */
public class UserResource extends ResourceSupport {
    private Integer userId;
    private String userName;
    private String trueName;
    private String roles;
    private String orgIds;
    private String orgNames;
    private Integer postId;
    private String lastLoginIp;
    private Boolean locked;
    private LocalDateTime lastLoginTime;
    private Integer lockedNumber;
    private String  mobile;
    private Boolean            promoted;
    private Integer            promotedSeq;
    private LocalDateTime      promotedTime;
    private String email;
    private LocalDateTime createTime;
    private String createIp;
    private String            idcard;
    private UserProfileGender gender;
    private String            iam;
    private Date birthday;
    private String            city;
    private String            qq;
    private String            signature;
    private String            about;
    private String            company;
    private String            job;
    private String            school;
    private String            class_;
    private String            weibo;
    private String            weixin;
    private Integer           isQqPublic;
    private Integer           isWeixinPublic;
    private Integer           isWeiboPublic;
    private String            site;
    private String post;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getIam() {
        return iam;
    }

    public void setIam(String iam) {
        this.iam = iam;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
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

    public Integer getIsQqPublic() {
        return isQqPublic;
    }

    public void setIsQqPublic(Integer isQqPublic) {
        this.isQqPublic = isQqPublic;
    }

    public Integer getIsWeixinPublic() {
        return isWeixinPublic;
    }

    public void setIsWeixinPublic(Integer isWeixinPublic) {
        this.isWeixinPublic = isWeixinPublic;
    }

    public Integer getIsWeiboPublic() {
        return isWeiboPublic;
    }

    public void setIsWeiboPublic(Integer isWeiboPublic) {
        this.isWeiboPublic = isWeiboPublic;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getLockedNumber() {
        return lockedNumber;
    }

    public void setLockedNumber(Integer lockedNumber) {
        this.lockedNumber = lockedNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getPromoted() {
        return promoted;
    }

    public void setPromoted(Boolean promoted) {
        this.promoted = promoted;
    }

    public Integer getPromotedSeq() {
        return promotedSeq;
    }

    public void setPromotedSeq(Integer promotedSeq) {
        this.promotedSeq = promotedSeq;
    }

    public LocalDateTime getPromotedTime() {
        return promotedTime;
    }

    public void setPromotedTime(LocalDateTime promotedTime) {
        this.promotedTime = promotedTime;
    }
}
