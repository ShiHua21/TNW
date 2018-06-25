/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.User;

import com.jic.tnw.user.service.dto.AddUserDTO;
import com.jic.tnw.user.service.dto.UserSetRoleGroup;
import com.jic.tnw.user.service.dto.user.EditUserInfoDTO;
import com.jic.tnw.user.service.dto.user.JelUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface UserService {


    JelUser add(AddUserDTO addUserDTO);

    JelUser findById(Integer id);

    User findByPrincipal(String principal);

    User findByUsername(String userName);

    Boolean existsPhoneNo(String phoneNo);

    String generateUserName();

    Page<User> findWithPageable(Pageable pageable, Map<String, Object> conditionMap);

    void updateLoginSuccess(String principal, String ip);

    void updateLoginFailure(String principal);

    JelUser updateOrgByUserId(Integer id, Integer uid, String[] orgIds);

    JelUser updatePostID(Integer postid, Integer uid);

    void updateOrgByUserIds(String[] ids, Integer uid, String[] orgIds);

    void updatePostByUserIds(String[] ids, Integer uid, Integer postId);

    User findByName(String name);

    User findByEmail(String email);

    JelUser updateUserByRoleId(UserSetRoleGroup userSetRoleGroup, Integer id);

    Long countByOrg(Integer orgId);

    Long countByPost(Integer postId);

    void lock(Integer id);

    void unlock(Integer id);

    //    Long countByType(String type);
    Integer countForLocked();

    Long countByAllUser();

    /**
     * 修改用户名
     * @param name 姓名
     * @param id 用户id
     * @return user
     */
    JelUser editUserName(String name,Integer id);
    /**
     * 修改用户密码
     * @param pwd 密码
     * @param id 用户id
     * @return user
     */
    JelUser editUserPwd(String pwd,Integer id);

    /**
     * @param editUserInfoDTO 编辑信息
     * @return jeluser
     */
    JelUser updateUserProfile(EditUserInfoDTO editUserInfoDTO);

    /**
     * 修改用户头像
     * @param imageString base64
     * @param id 用户id
     * @return large small medium Avatar
     */
//    User editUserAvatar(String imageString,Integer id);

    /**
     * 设置/取消 推荐老师
     * @param id
     * @param promoted
     * @return
     */
    User setPromotedTeacher(Integer id,Boolean promoted);

    /**
     * 设置推荐老师排序号
     * @param id
     * @param seq
     * @return
     */
    User setPromotedSeq(Integer id,Integer seq);

    /**
     *
     * @param postId
     * @param id
     * @return
     */
    JelUser setPostByPostIdUpdatePostMember(Integer postId,Integer id);


    /**
     * 查询老师
     * @param userName
     * @return
     */
    List<User> findTeachersByUserName(String userName);

}
