package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.UserOrg;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface UserOrgRepository extends JooqRepository<UserOrg> {

    void deleteByUserId(Integer userId);
}
