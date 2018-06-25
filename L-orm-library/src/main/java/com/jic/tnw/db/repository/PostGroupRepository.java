package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;

public interface PostGroupRepository extends JooqRepository<PostGroup> {

    PostGroup findByName(String name);

}
