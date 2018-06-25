package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Category;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface CategoryRepository extends JooqRepository<Category> {

    Category findByCode(String code);

    List<Category> findListByGroupId(Integer groupId);

    void updateCtierCodeById(Integer Id,String ctierCode);

    void deleteLikeTierCode(String tierCode);


}
