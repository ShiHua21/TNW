package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.UserGroup;

/**
 * @author lee5hx
 */
public interface UserGroupRepository extends JooqRepository<UserGroup> {
    UserGroup findByCode(String code);
}
