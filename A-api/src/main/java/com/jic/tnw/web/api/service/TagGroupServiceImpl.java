package com.jic.tnw.web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.TagGroupIdNotFoundException;
import com.jic.tnw.common.exception.TagGroupNameExistException;
import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.tnw.db.mysql.tables.pojos.TagGroupTag;
import com.jic.tnw.db.repository.impl.JooqTagGroupRepository;
import com.jic.tnw.db.repository.impl.JooqTagGroupTagRepository;
import com.jic.tnw.db.repository.impl.JooqTagRepository;
import com.jic.tnw.user.service.RoleServiceImpl;
import com.jic.elearning.web.api.vo.request.Tag.AddTagGroup;
import com.jic.tnw.web.api.vo.request.Tag.AddTagGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/***
 * @author tp
 * @date 2018/4/2
 */
@Service
public class TagGroupServiceImpl implements TagGroupService {

    private static final Log LOGGER = LogFactory.getLog(RoleServiceImpl.class);
    private final ObjectMapper mapper;
    private final JooqTagGroupRepository jooqTagGroupRepository;
    private final JooqTagRepository jooqTagRepository;
    private final JooqTagGroupTagRepository jooqTagGroupTagRepository;

    @Autowired
    public TagGroupServiceImpl(ObjectMapper objectMapper, JooqTagGroupRepository jooqTagGroupRepository, JooqTagRepository jooqTagRepository, JooqTagGroupTagRepository jooqTagGroupTagRepository) {
        this.mapper = objectMapper;
        this.jooqTagGroupRepository = jooqTagGroupRepository;
        this.jooqTagRepository = jooqTagRepository;
        this.jooqTagGroupTagRepository = jooqTagGroupTagRepository;
    }

    @Override
    public void verifyAddTagGroupName(String name) {
        TagGroup tagGroup = jooqTagGroupRepository.findByName(name);
        if (tagGroup != null) {
            throw new TagGroupNameExistException();
        }
    }

    @Override
    public void verifyEditTagGroupName(String name, Integer id) {
        TagGroup tagGroupById = jooqTagGroupRepository.findById(id);
        if (tagGroupById == null) {
            throw new TagGroupIdNotFoundException();
        }
        TagGroup tagGroupByName = jooqTagGroupRepository.findByName(name);
        if (tagGroupByName == null) {

        } else {
            if (tagGroupByName.getName().equals(tagGroupById.getName())) {

            } else {
                throw new TagGroupNameExistException();
            }
        }
    }

    @Override
    public TagGroup addTagGroup(AddTagGroup addTagGroup) {
        verifyAddTagGroupName(addTagGroup.getName());
        String result = getScope(addTagGroup.getScope());
        String key_pre = ",";
        String[] nums = addTagGroup.getTags().split(key_pre);
        List<Integer> tagIdList = getTagIdList(nums);
        TagGroup tagGroup = new TagGroup();
        tagGroup.setTagNum(tagIdList.size());
        tagGroup.setScope(result.toString());
        tagGroup.setName(addTagGroup.getName());
        TagGroup newBuildTagGtoup = jooqTagGroupRepository.add(tagGroup);
        TagGroupTag tagGroupTag = new TagGroupTag();
        tagGroupTag.setGroupId(newBuildTagGtoup.getId());
        for (Integer tagId : tagIdList) {
            tagGroupTag.setTagId(tagId);
            jooqTagGroupTagRepository.add(tagGroupTag);
        }
        return newBuildTagGtoup;
    }

    @Override
    public TagGroup update(AddTagGroup addTagGroup, Integer id) {
        verifyEditTagGroupName(addTagGroup.getName(), id);
        String result = getScope(addTagGroup.getScope());
        String key_pre = ",";
        String[] nums = addTagGroup.getTags().split(key_pre);
        List<Integer> tagIdListUpdateBefore = jooqTagGroupTagRepository.findByTagGroupId(id);
        for (Integer tagId : tagIdListUpdateBefore) {
            jooqTagGroupTagRepository.deleteByTagId(tagId);
        }
        List<Integer> tagIdList = getTagIdList(nums);
        TagGroup tagGroup = new TagGroup();
        tagGroup.setTagNum(tagIdList.size());
        tagGroup.setScope(result.toString());
        tagGroup.setName(addTagGroup.getName());
        tagGroup.setId(id);
        TagGroup updateTagGroup = jooqTagGroupRepository.update(tagGroup);
        for (Integer tagId : tagIdList) {
            TagGroupTag tagGroupTag = new TagGroupTag();
            tagGroupTag.setTagId(tagId);
            tagGroupTag.setGroupId(updateTagGroup.getId());
            jooqTagGroupTagRepository.add(tagGroupTag);
        }
        return updateTagGroup;
    }

    private List<Integer> getTagIdList(String[] nums) {
        List<Integer> tagIdList = new ArrayList<>();

        for (String str : nums) {
            Tag tag = jooqTagRepository.findByName(str);
            if (tag == null) {
                Tag addTag = new Tag();
                addTag.setName(str);
                Tag tagNewByName = jooqTagRepository.add(addTag);
                tagIdList.add(tagNewByName.getId());
            } else {
                tagIdList.add(tag.getId());
            }
        }
        return tagIdList;
    }

    private String getScope(List<Integer> scopeList) {
        if (scopeList.size()==2){
            return "课程列表筛选,专题列表筛选";
        }else {
            Integer topic = scopeList.get(0);
            if (topic==0){
                return "课程列表筛选";
            }else {
                return "专题列表筛选";
            }

        }
    }

    @Override
    public List<TagGroup> findAll() {
        return jooqTagGroupRepository.findAll();
    }

    @Override
    public TagGroup deleteTagGroup(Integer id) {

        TagGroup tagGroup = findById(id);
        List<Integer> tagIdListUpdateBefore = jooqTagGroupTagRepository.findByTagGroupIdGetTagGroupNum(id);
        for (Integer tagGroupTagId : tagIdListUpdateBefore){
            jooqTagGroupTagRepository.delete(tagGroupTagId);
        }
        return jooqTagGroupRepository.delete(id);
    }

    @Override
    public TagGroup findById(Integer id) {
        TagGroup tagGroup = jooqTagGroupRepository.findById(id);
        if (tagGroup == null) {
            throw new TagGroupIdNotFoundException();
        }
        return tagGroup;
    }
    @Override
    public Page<TagGroup> findWithPageable(Pageable pageable){
        return jooqTagGroupRepository.findWithPageable(pageable);
    }
}
