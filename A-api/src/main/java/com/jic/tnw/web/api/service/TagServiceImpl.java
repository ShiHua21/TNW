package com.jic.tnw.web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.TagIdNotFoundException;
import com.jic.tnw.common.exception.TagNameExistsException;
import com.jic.tnw.db.mysql.tables.pojos.Tag;
import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import com.jic.tnw.db.repository.impl.JooqTagGroupRepository;
import com.jic.tnw.db.repository.impl.JooqTagGroupTagRepository;
import com.jic.tnw.db.repository.impl.JooqTagRepository;
import com.jic.tnw.user.service.RoleServiceImpl;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import com.jic.tnw.web.api.vo.request.Tag.TagVerifyName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    private static final Log LOGGER = LogFactory.getLog(RoleServiceImpl.class);
    private final ObjectMapper mapper;
    private final JooqTagRepository jooqTagRepository;
    private final JooqTagGroupTagRepository jooqTagGroupTagRepository;
    private final JooqTagGroupRepository jooqTagGroupRepository;
    @Autowired
    public TagServiceImpl(ObjectMapper objectMapper, JooqTagRepository jooqTagRepository, JooqTagGroupTagRepository jooqTagGroupTagRepository, JooqTagGroupRepository jooqTagGroupRepository){
        this.mapper = objectMapper;
        this.jooqTagRepository = jooqTagRepository;
        this.jooqTagGroupTagRepository = jooqTagGroupTagRepository;
        this.jooqTagGroupRepository = jooqTagGroupRepository;
    }
    @Override
    public void verifyAddTagName(String name){
        Tag tag = jooqTagRepository.findByName(name);
        if (tag != null){
            throw new TagNameExistsException();
        }
    }
    @Override
    public void verifyEditName(String name,Integer id){
        Tag tagById = jooqTagRepository.findById(id);
        if (tagById==null){
            throw new TagIdNotFoundException();
        }
        Tag tagByName = jooqTagRepository.findByName(name);
        if (tagByName==null){

        }else {
            if (tagByName.getName().equals(tagById.getName())){

            }else {
                throw new TagNameExistsException();
            }
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tag addTag(String name){
        Tag tag =jooqTagRepository.findByName(name);
        if (tag != null){
            throw new TagNameExistsException();
        }
        Tag entry = new Tag();
        entry.setName(name);
        Tag addTag = jooqTagRepository.add(entry);
        return addTag;
    }
    @Override
    public List<Tag> findAll(){
        return jooqTagRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tag deleteTag(Integer id){
        Tag tag = findById(id);
        List<Integer> tagGroupTagIdList = jooqTagGroupTagRepository.findByTagIdGetTagNum(id);
        List<Integer> tagGroupIdList = jooqTagGroupTagRepository.findByTagId(id);
        for (Integer tagGrouTagId : tagGroupTagIdList ){
            jooqTagGroupTagRepository.delete(tagGrouTagId);
        }
        for(Integer tagGroupId:tagGroupIdList){
            jooqTagGroupRepository.updateTagNum(tagGroupId);
        }
        return jooqTagRepository.delete(id);
    }

    @Override
    public Tag findById(Integer id){
        Tag tag = jooqTagRepository.findById(id);
        if (tag==null){
            throw  new TagIdNotFoundException();
        }
        return tag;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tag updateTag(TagVerifyName verifyName, Integer id){
        Tag tag = findById(id);
        if (tag == null){
            throw  new TagIdNotFoundException();
        }
        verifyEditName(verifyName.getName(),id);
        Tag updateTag = new Tag();
        updateTag.setName(verifyName.getName());
        updateTag.setId(id);
        return jooqTagRepository.update(updateTag);
    }
    @Override
    public List<TagGroup> findByTagId(Integer id){
         List<Integer>list = jooqTagGroupTagRepository.findByTagId(id);
         List<TagGroup> tagGroupList = new ArrayList<>();
         for (Integer groupId : list){
           TagGroup tagGroup = jooqTagGroupRepository.findById(groupId);
           tagGroupList.add(tagGroup);
         }
         return tagGroupList;
    }
    @Override
    public List<Tag> searchTagByName(String name){
        List<Tag> tagList = jooqTagRepository.findByContainsTrueName(name);
        return tagList;

    }
    @Override
    public Page<Tag> findWithPageable(Pageable pageable){

        return jooqTagRepository.findWithPageable(pageable);
    }
}
