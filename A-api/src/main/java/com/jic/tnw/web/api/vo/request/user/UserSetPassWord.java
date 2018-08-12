package com.jic.tnw.web.api.vo.request.user;

import org.hibernate.validator.constraints.NotBlank;

public class UserSetPassWord {

    @NotBlank(message = "{user.oldpassword.not.blank}")
    private String oldpassword;

    /**
     * password
     * user.password.not.blank = 密码不能为空!
     */
    @NotBlank(message = "{user.password.not.blank}")
    private String password;

    /**
     * q2password
     * user.q2password.not.blank = 确认密码不能为空!
     */
    @NotBlank(message = "{user.q2password.not.blank}")
    private String q2password;

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQ2password() {
        return q2password;
    }

    public void setQ2password(String q2password) {
        this.q2password = q2password;
    }
}
