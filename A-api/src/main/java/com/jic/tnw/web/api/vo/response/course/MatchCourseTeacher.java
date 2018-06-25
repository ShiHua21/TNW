package com.jic.tnw.web.api.vo.response.course;

/**
 * Created by lee5hx on 2018/4/25.
 */
public class MatchCourseTeacher {
    //[{
    //    "id": "9",
    //            "nickname": "\u4e1c\u65b91",
    //            "avatar": "\/assets\/img\/default\/avatar.png",
    //            "isVisible": 1
    //}, {
    //    "id": "3",
    //            "nickname": "lee5hx01",
    //            "avatar": "\/files\/default\/2018\/04-08\/110546a3905e318976.jpeg",
    //            "isVisible": 1
    //}]

    private Integer id;
    private String userName;
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
