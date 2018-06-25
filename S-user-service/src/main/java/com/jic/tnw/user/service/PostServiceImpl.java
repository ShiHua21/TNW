package com.jic.tnw.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.PostCodeNotExistException;
import com.jic.tnw.common.exception.PostGroupNotFoundException;
import com.jic.tnw.common.exception.PostNameAleadyOccupiedException;
import com.jic.tnw.common.exception.PostNotFoundException;
import com.jic.tnw.db.mysql.tables.pojos.Post;
import com.jic.tnw.db.mysql.tables.pojos.PostGroup;
import com.jic.tnw.db.repository.PostGroupRepository;
import com.jic.tnw.db.repository.PostRepository;

import com.jic.tnw.user.service.dto.post.AddPostDTO;
import com.jic.tnw.user.service.dto.post.EditPostDTO;
import com.jic.tnw.user.service.dto.post.VerifyPostCode;
import com.jic.tnw.user.service.dto.post.VerifyPostName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Log LOGGER = LogFactory.getLog(OrgServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;
    private final PostGroupRepository postGroupRepository;

    @Autowired
    PostServiceImpl(ObjectMapper objectMapper, PostRepository postRepository, PostGroupRepository postGroupRepository) {
        this.objectMapper = objectMapper;
        this.postRepository = postRepository;
        this.postGroupRepository = postGroupRepository;
    }

    @Override
    @CachePut(value = "Post", key = "#result.id.toString()")
    public Post addPost(AddPostDTO addPost, Integer uid) throws Exception {
        if (postRepository.findByCode(addPost.getCode()) != null) {
            throw new PostCodeNotExistException();
        }
        String groupID = addPost.getGroupId();
        LOGGER.info(String.format("group_id ========== %s  uid =====%s", groupID, uid));
        PostGroup postGroup = postGroupRepository.findById(Integer.valueOf(groupID));
        if (postGroup == null) {
            throw new PostGroupNotFoundException();
        }
        Post post = new Post();
        post.setCreatedTime(LocalDateTime.now());
        post.setName(addPost.getName());
        post.setGroupId(Integer.valueOf(addPost.getGroupId()));
        post.setCode(addPost.getCode());
        post.setSeq(0);
        post.setCreatedUserId(uid);
        post.setCreatedTime(LocalDateTime.now());
        post = postRepository.add(post);
        return post;
    }

    @Override
    @CacheEvict(value = "Post", key = "#id.toString()")
    public Post delPost(Integer id, Integer uid) throws Exception {

        Post post = postRepository.findById(id);

        //todo another judge by user  -----TP
        if (post == null) {
            throw new PostNotFoundException();
        }
        Post delPost = postRepository.delete(id);
        return delPost;
    }

    @Override
    @CachePut(value = "Post", key = "#id.toString()")
    public Post updatePost(Integer id, EditPostDTO editPost, Integer uid) throws Exception {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException();
        }
        Post code_post = postRepository.findByCode(editPost.getCode());
        Post name_post = postRepository.findByName(editPost.getName());
        if (code_post != null && !code_post.getId().equals(post.getId())){
            throw new PostCodeNotExistException();
        }
        if (name_post != null && !name_post.getId().equals(post.getId())){
            throw new PostNameAleadyOccupiedException();
        }
        Post updatepost = new Post();
        updatepost.setCode(editPost.getCode());
        updatepost.setName(editPost.getName());
        updatepost.setLastUpdUserId(uid);
        updatepost.setId(id);
        return postRepository.updateNameAndCode(updatepost);

    }

    @Override
    public Post verifyNamePost(VerifyPostName verifyPostName) throws Exception {
        LOGGER.debug(String.format("id======%s  group-id===%s name====%s ", verifyPostName.getId(), verifyPostName.getGroupID(), verifyPostName.getName()));
        Post post = postRepository.findById(verifyPostName.getId());
        if (post == null) {
            throw new PostNotFoundException();
        }
        Post name_post = postRepository.findByName(verifyPostName.getName());
        if (name_post == null) {
            return post;
        } else {
            if (post.getId().equals(name_post.getId())) {
                return name_post;
            }
            throw new PostCodeNotExistException();
        }

    }

    @Override
    public Post verifyCodePost(VerifyPostCode verifyPostCode) throws Exception {
        LOGGER.debug(String.format("id======%s  group-id===%s name====%s ", verifyPostCode.getId(), verifyPostCode.getGroupID(), verifyPostCode.getCode()));
        Post post = postRepository.findById(verifyPostCode.getId());
        if (post == null) {
            throw new PostNotFoundException();
        }
        Post code_post = postRepository.findByCode(verifyPostCode.getCode());
        if (code_post == null) {
            return code_post;
        } else {
            if (post.getId().equals(code_post.getId())) {
                return code_post;
            }
            throw new PostNameAleadyOccupiedException();

        }

    }

    @Override
    @Cacheable(value = "Post",key = "#id.toString()")
    public Post getPost(Integer id) throws Exception {
        LOGGER.debug(String.format("id ============ %s", id));
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException();
        }
        return post;
    }

    @Override
    public List<Post> findByGroupID(Integer gid, List<Post> aList) {
//        List<Post> list = postRepository.findAll();
//        LOGGER.info(String.format("User.Org OrgTreeResource.List.Size=%d ",
//                list.size()
//        ));
        Map<Integer, List<Post>> map = aList.stream()
                .collect(Collectors.groupingBy(Post::getGroupId));
        List<Post> plist = map.get(gid);
        return plist;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Post> findAll() {
        List<Post> list = postRepository.findAll();
        return list;
    }

    @Override
    @Transactional(propagation =  Propagation.REQUIRED)
    public Post findByID (Integer id){
        Post post = postRepository.findById(id);
        if (post == null){
            throw new PostNotFoundException();
        }
        return post;
    }
    @Override
    @Transactional(propagation =  Propagation.REQUIRED)
    public Post userFindById(Integer id){
        Post post = postRepository.findById(id);
        return post;
    }

//    @Override
//    @Cacheable(value = "Post",key = "#ALL")
//    @Transactional(propagation = Propagation.REQUIRED)
//    public Map<Integer,Post> getAllPostMap(){
//        List<Post> list = postRepository.findAll();
//        Map<Integer, Post> postMap = list.parallelStream()
//                .collect(HashMap::new, (map, p) -> map.put(p.getId(), p), Map::putAll);
//        return postMap;
//    }

}
