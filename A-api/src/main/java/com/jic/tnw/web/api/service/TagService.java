package com.jic.tnw.web.api.service;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import com.jic.tnw.web.api.vo.request.Tag.TagVerifyName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
/***
 * @author tp
 * @date 2018/4/2
 */

public interface TagService {

    void verifyAddTagName(String name);

    void verifyEditName(String name,Integer id);

    Tag addTag(String name);

    List<Tag> findAll();

    Tag deleteTag(Integer id);

    Tag findById(Integer id);

    Tag updateTag(TagVerifyName tagVerifyName, Integer id);

    List<TagGroup> findByTagId(Integer id);

    List<Tag> searchTagByName(String name);

    Page<Tag> findWithPageable(Pageable pageable);
//    org.springframework.data.domain.Page<Tag> find(Pageable pageable);
}
