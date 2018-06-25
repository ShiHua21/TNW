package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface RoleRepository extends JooqRepository<Role> {

    Page<Role> find(Pageable pageable,Map<String, Object> conditionMap);

    Role findByCode(String code);
    Role findByName(String name);
    Role addRole(String name,String code,String json,Integer id);
    Role updateRole(String name,Integer uid,String json,Integer roleId);
}
