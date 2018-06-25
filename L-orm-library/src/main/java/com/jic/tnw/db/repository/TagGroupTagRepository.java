package com.jic.tnw.db.repository;


import com.jic.tnw.db.mysql.tables.pojos.TagGroupTag;

import java.util.List;

/**
 * @author tp
 */
public interface TagGroupTagRepository extends JooqRepository<TagGroupTag> {

    List<Integer> findByTagGroupId(Integer id);

    List<Integer> findByTagId(Integer id);

    TagGroupTag deleteByTagId(Integer id);

    TagGroupTag deleteByTagGroupId(Integer id);

    List<Integer> findByTagIdGetTagNum(Integer id);

    List<Integer> findByTagGroupIdGetTagGroupNum(Integer id);
}
