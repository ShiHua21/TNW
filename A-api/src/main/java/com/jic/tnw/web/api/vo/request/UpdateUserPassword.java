package com.jic.tnw.web.api.vo.request;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class UpdateUserPassword {
    @NotBlank(message = "{update.user.oldpass.not.blank}")
    private String oldPassword;
    @NotBlank(message = "{update.user.newpass.not.blank}")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
