package com.jic.tnw.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.PostGrouNameNotExistsException;
import com.jic.tnw.common.exception.PostGroupCanNotDelException;
import com.jic.tnw.common.exception.PostGroupNotFoundException;
import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.tnw.db.repository.PostGroupRepository;
import com.jic.tnw.db.repository.PostRepository;
import com.jic.tnw.user.service.dto.post.AddPostGroupDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostGroupServiceImpl implements PostGroupService {

    private static final Log LOGGER = LogFactory.getLog(OrgServiceImpl.class);

    private final ObjectMapper mapper;

    private final PostGroupRepository postGroupRepository;

    private final PostRepository postRepository;

    @Autowired
    PostGroupServiceImpl(ObjectMapper mapper,
                         PostGroupRepository postGroupRepository, PostRepository postRepository) {
        this.mapper = mapper;
        this.postGroupRepository = postGroupRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostGroup addPostGroup(AddPostGroupDTO addPostGroup, Integer uid) throws Exception {

        if (postGroupRepository.findByName(addPostGroup.getName()) != null) {
            throw new PostGrouNameNotExistsException();
        }
        LOGGER.info(String.format("createPostGroup === %s  uid  ======= %s", addPostGroup, uid));
        PostGroup postGroup = new PostGroup();
        postGroup.setName(addPostGroup.getName());
        postGroup.setCreatedTime(LocalDateTime.now());
        postGroup.setCreatedUserId(uid);
        postGroup.setSeq(0);
        postGroup = postGroupRepository.add(postGroup);
        return postGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PostGroup delPostGroup(Integer group_id) throws Exception {
        int size = postRepository.findByGroupID(group_id).size();
        LOGGER.debug(String.format("size====== %s", size));
        if (size > 0) {
            throw new PostGroupCanNotDelException();
        }
        PostGroup postGroup = postGroupRepository.delete(group_id);
        return postGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PostGroup updateGroup(Integer id, String name, Integer uid) throws Exception {
        LOGGER.debug(String.format("id=====%s name=====%s   uid =======%s ", id, name, uid));
        PostGroup postGroup = postGroupRepository.findById(id);
        if (postGroup == null) {
            throw new PostGroupNotFoundException();
        }
        PostGroup namePostGroup = postGroupRepository.findByName(name);
        if (namePostGroup != null && !postGroup.getName().equals(name)) {
            throw new PostGrouNameNotExistsException();
        }
        PostGroup updateGroup = new PostGroup();
        if (postGroup.getName().equals(name)) {

        } else {
            updateGroup.setName(name);
            updateGroup.setId(id);
            updateGroup.setSeq(0);
            updateGroup.setLastUpdUserId(uid);
            updateGroup.setLastUpdTime(LocalDateTime.now());
            postGroupRepository.update(updateGroup);
        }
        return postGroupRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PostGroup getPostGroup(Integer id) throws Exception {
        LOGGER.debug(String.format("id=============%s", id));
        PostGroup postGroup = postGroupRepository.findById(id);
        if (postGroup == null) {
            throw new PostGroupNotFoundException();
        }
        return postGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<PostGroup> findAll() {
        List<PostGroup> list = postGroupRepository.findAll();
        return list;
    }

}
