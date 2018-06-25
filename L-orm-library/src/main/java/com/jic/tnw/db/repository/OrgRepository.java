package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Org;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface OrgRepository extends JooqRepository<Org> {

    void updateChildrenNumById(Integer id,Integer childrenNum);
    void updateOrgCodeById(Integer id,String orgCode);
    Org findByCode(String code);
    List<Org> findByContainsName(String name);

}
