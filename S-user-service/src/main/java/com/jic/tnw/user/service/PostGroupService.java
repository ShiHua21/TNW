package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.tnw.user.service.dto.post.AddPostGroupDTO;

import java.util.List;

public interface PostGroupService {

    PostGroup addPostGroup(AddPostGroupDTO createPostGroup, Integer uid)throws Exception;

    PostGroup delPostGroup(Integer group_id)throws Exception;

    PostGroup updateGroup(Integer id,String name, Integer uid) throws Exception;

    PostGroup getPostGroup(Integer id)throws Exception;

    List<PostGroup> findAll();
}
