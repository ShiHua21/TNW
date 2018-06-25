/*
 * This file is generated by jOOQ.
*/
package com.jic.tnw.db.mysql;


import com.jic.tnw.db.mysql.tables.Log;
import com.jic.tnw.db.mysql.tables.Org;
import com.jic.tnw.db.mysql.tables.Post;
import com.jic.tnw.db.mysql.tables.PostGroup;
import com.jic.tnw.db.mysql.tables.PostMember;
import com.jic.tnw.db.mysql.tables.Role;
import com.jic.tnw.db.mysql.tables.Tasks;
import com.jic.tnw.db.mysql.tables.User;
import com.jic.tnw.db.mysql.tables.UserGroup;
import com.jic.tnw.db.mysql.tables.UserGroupMember;
import com.jic.tnw.db.mysql.tables.UserOrg;
import com.jic.tnw.db.mysql.tables.UserProfile;
import com.jic.tnw.db.mysql.tables.WebUrlResource;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in TNW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * ???????
     */
    public static final Log LOG = com.jic.tnw.db.mysql.tables.Log.LOG;

    /**
     * ????
     */
    public static final Org ORG = com.jic.tnw.db.mysql.tables.Org.ORG;

    /**
     * ??
     */
    public static final Post POST = com.jic.tnw.db.mysql.tables.Post.POST;

    /**
     * ????
     */
    public static final PostGroup POST_GROUP = com.jic.tnw.db.mysql.tables.PostGroup.POST_GROUP;

    /**
     * ????
     */
    public static final PostMember POST_MEMBER = com.jic.tnw.db.mysql.tables.PostMember.POST_MEMBER;

    /**
     * ???
     */
    public static final Role ROLE = com.jic.tnw.db.mysql.tables.Role.ROLE;

    /**
     * ???
     */
    public static final Tasks TASKS = com.jic.tnw.db.mysql.tables.Tasks.TASKS;

    /**
     * ???
     */
    public static final User USER = com.jic.tnw.db.mysql.tables.User.USER;

    /**
     * ???
     */
    public static final UserGroup USER_GROUP = com.jic.tnw.db.mysql.tables.UserGroup.USER_GROUP;

    /**
     * ?????
     */
    public static final UserGroupMember USER_GROUP_MEMBER = com.jic.tnw.db.mysql.tables.UserGroupMember.USER_GROUP_MEMBER;

    /**
     * ????????
     */
    public static final UserOrg USER_ORG = com.jic.tnw.db.mysql.tables.UserOrg.USER_ORG;

    /**
     * ?????
     */
    public static final UserProfile USER_PROFILE = com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE;

    /**
     * web_url???
     */
    public static final WebUrlResource WEB_URL_RESOURCE = com.jic.tnw.db.mysql.tables.WebUrlResource.WEB_URL_RESOURCE;
}
