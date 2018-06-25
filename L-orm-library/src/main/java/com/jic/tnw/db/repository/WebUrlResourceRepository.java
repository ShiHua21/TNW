package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.pojos.WebUrlResource;

import java.util.List;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface WebUrlResourceRepository extends JooqRepository<WebUrlResource> {



    List<WebUrlResource> findListByType(WebUrlResourceType type);
}
