package com.jic.tnw.user.service.dto.user;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.db.mysql.tables.pojos.UserProfile;

/**
 * Created by lee5hx on 2018/4/8.
 */
public class JelUser {

    private User user;

    private UserProfile userExtension;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile getUserExtension() {
        return userExtension;
    }

    public void setUserExtension(UserProfile userExtension) {
        this.userExtension = userExtension;
    }
}
