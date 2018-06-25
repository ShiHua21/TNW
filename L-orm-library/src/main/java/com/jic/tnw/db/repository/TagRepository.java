package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tp
 * @date 2018/4/2
 */
public interface TagRepository extends JooqRepository<Tag> {
    Tag findByName(String name);

    List<Tag> findByContainsTrueName(String name);

    Page<Tag> findWithPageable(Pageable pageable);
}
