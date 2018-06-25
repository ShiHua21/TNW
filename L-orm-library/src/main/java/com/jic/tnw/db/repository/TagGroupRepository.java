package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author tp
 */
public interface TagGroupRepository  extends JooqRepository<TagGroup> {
    TagGroup findByName(String name);

    Page<TagGroup> findWithPageable(Pageable pageable);

    TagGroup updateTagNum(Integer id);
}
