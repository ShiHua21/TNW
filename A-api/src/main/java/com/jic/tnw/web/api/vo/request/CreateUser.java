package com.jic.tnw.web.api.vo.request;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class CreateUser {
    @NotBlank(message = "{auth.create.user.username.not.blank}")
    private String username;
    @NotBlank(message = "{auth.create.user.password.not.blank}")
    private String password;
    @NotBlank(message = "{auth.create.user.email.not.blank}")
    private String email;
//    @NotBlank(message = "{auth.create.user.avatar.not.blank}")
//    private String avatar;

    public CreateUser() {

    }

    public CreateUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User buildUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(email);
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
