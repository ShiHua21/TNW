package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.user.service.dto.post.AddPostDTO;
import com.jic.tnw.user.service.dto.post.EditPostDTO;
import com.jic.tnw.user.service.dto.post.VerifyPostCode;
import com.jic.tnw.user.service.dto.post.VerifyPostName;

import java.util.List;

public interface PostService {
    /**
     *
     * @param addPost
     * @param uid
     * @return
     * @throws Exception
     */
    Post addPost(AddPostDTO addPost, Integer uid) throws Exception;
    Post delPost(Integer id,Integer uid)throws Exception;
    Post updatePost(Integer id, EditPostDTO editPost, Integer uid)throws Exception;
    Post verifyNamePost(VerifyPostName verifyPostName)throws Exception;
    Post verifyCodePost(VerifyPostCode verifyPostCode)throws Exception;
    Post getPost (Integer id)throws Exception;
    List<Post> findByGroupID (Integer gid,List<Post> aList);
    List<Post> findAll();
    Post findByID (Integer id);

    /***
     * tp
     * @param id
     * @return 不判断post是否为空 抛出异常
     */
    Post userFindById(Integer id);

}
