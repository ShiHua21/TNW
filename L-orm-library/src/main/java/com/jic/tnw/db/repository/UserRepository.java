package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author lee5hx
 * Created by lee5hx on 2017/10/30.
 */
public interface UserRepository extends JooqRepository<User> {

    /**
     * 使用手机号查找用户
     * @param mobile
     * @return
     */
    User findUserByMobile(String mobile);

    /**
     * 修改密码
     * @param userName
     * @param password
     */
    void updatePassword(String userName, String password);

    /**
     * 生成用户名
     * @return
     */
    String generateUserName();

    /**
     * 通过中主要凭证(username,email,mobile)查询用户
     * @param principal
     * @return
     */
    User findByPrincipal(String principal);

    /**
     * 用户列表分页接口
     * @param conditionMap
     * @param pageable
     * @return
     */
    Page<User> find(Map<String, Object> conditionMap, Pageable pageable);

    /**
     * 更新登陆成功信息
     * @param principal
     * @param ip
     */
    void updateLoginSuccess(String principal, String ip);

    /**
     * 更新登陆失败信息
     * @param principal
     */
    void updateLoginFailure(String principal);

    /**
     * 根据ID更组织机构
     * @param id
     * @param orgIds
     * @param orgCodes
     */
    void updateOrgById(Integer id, String orgIds, String orgCodes);

    /**
     * 根据ID更组织岗位
     * @param id
     * @param postId
     */
    void updatePostById(Integer id, Integer postId);

    /**
     * 根据name获取用户
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 根据email获取用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     *
     * @param trueName
     * @return
     */
    List<User> findByContainsTrueName(String trueName);

    /**
     *
     * @param orgId
     * @return
     */
    Long countByOrg(Integer orgId);

    /**
     *
     * @param postId
     * @return
     */
    Long countByPost(Integer postId);

    /**
     *
     * @param roles
     * @param id
     * @return
     */
    User updateRolesById(String roles, Integer id);

    /**
     *
     * @param id
     * @param lock
     */
    void updateLockById(Integer id, Boolean lock);

    /**
     *
     * @return
     */
    Integer findByContainsLocked();

    /***
     *  获取用户总数
     */
    long countByAllUser();

    /**
     * 更新用户名
     * @param name 姓名
     * @param id 用户id
     * @return user
     */
    User updateUserName(String name,Integer id);

    /**
     * 更新user密码
     * @param pwd 姓名
     * @param id 用户id
     * @return user
     */
    User updateUserPwd(String pwd,Integer id);

    /**
     * xxx
     * @param promoted
     * @param id
     */
    void updatePromoted(Integer id,Boolean promoted);

    /**
     * xxx
     * @param seq
     * @param id
     */
    void updatePromotedSeq(Integer id,Integer seq);


    /**
     * 更新user手机号
     * @param mobile 姓名
     * @param id 用户id
     * @return user
     */
    User updateUserMobile(String mobile,String turename,Integer id);


    /**
     * 查询老师
     * @param userName
     * @return
     */
    List<User> findTeachersByContainsUserName(String userName);

}
