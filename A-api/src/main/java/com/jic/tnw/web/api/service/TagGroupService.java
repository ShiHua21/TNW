package com.jic.tnw.web.api.service;

import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.elearning.web.api.vo.request.Tag.AddTagGroup;
import com.jic.tnw.web.api.vo.request.Tag.AddTagGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tp
 * @date 2018/4/2
 */
public interface TagGroupService {

    void verifyAddTagGroupName(String name);

    void verifyEditTagGroupName(String name, Integer id);

    TagGroup addTagGroup(AddTagGroup addTagGroup);

    List<TagGroup> findAll();

    TagGroup deleteTagGroup(Integer id);

    TagGroup findById(Integer id);

    TagGroup update(AddTagGroup addTagGroup, Integer id);

    Page<TagGroup> findWithPageable(Pageable pageable);

}
