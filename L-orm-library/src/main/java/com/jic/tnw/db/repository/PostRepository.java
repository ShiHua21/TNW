package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Post;

import java.util.List;

public interface PostRepository extends JooqRepository<Post> {

    Post findByCode(String code);
    Post findByName(String name);
    List<Post> findByGroupID(Integer group_id);
    Post updateNameAndCode(Post entry);

    List<Post> findByContainsName(String name);
}
