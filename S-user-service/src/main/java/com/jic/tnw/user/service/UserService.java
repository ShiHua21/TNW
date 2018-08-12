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

import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.user.service.dto.user.SetUserAvatarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {


    Boolean existsPhoneNo(String phoneNo);

    User findByphoneNo(String phoneNo);

    JelUser findById(String id);

    User findByPrincipal(String principal);

    /**
     *
     */
    DwMyMessage findMyMessageById(Integer id);

    /**
     *
     */
    DwOtherMessage findOtherMessageById(Integer id);

    /**
     * 我的留言  otherMessagePageable
     */
//    Page<DwMyMessage> selfMessagePageable(Pageable pageable,Map<String, Object> conditionMap );
    Page<DwMyMessage> selfMessagePageable(Pageable pageable, String id);

    /**
     * 删除我的留言
     */
    DwMyMessage deleteMyMessage(String ludelete, Integer id);

    /**
     * 他人留言  otherMessagePageable
     */
//    Page<DwMyMessage> otherMessagePageable(Pageable pageable,Map<String, Object> conditionMap );id
    Page<DwOtherMessage> otherMessagePageable(Pageable pageable, String id);

    /**
     * 删除ta的留言
     */
    DwOtherMessage deleteOtherMessage(String ludelete, Integer id);

    /**
     * 修改用户密码
     */
    JelUser oldUserPwd(String pwd, String id);

    /**
     * 修改用户密码
     */
    JelUser editUserPwd(String pwd, String id);

    /**
     * 忘记密码
     */
    JelUser userSetMobile(String phoneNo, String id);

    /**
     * 我的：机构动态
     */
    Page<DwOrgcircleDynamic> findUserDynamicById(Pageable pageable, String id);

    /**
     * 根据idcha动态
     */
    DwOrgcircleDynamic findDynamicById(Integer id);

    /**
     * 删除动态
     */
    DwOrgcircleDynamic deleteDynamic(String ludelete, Integer id);

    /**
     * 我的：活动
     */
    Page<DwOrgcircleActivity> findUserActivityById(Pageable pageable, String id);

    /**
     * 我的：活动
     */
    DwOrgcircleActivity findById(Integer id);

    /**
     * 我的：活动
     */
    DwOrgcircleActivity updateActivityById(DwOrgcircleActivity dwactivity,Integer id);

    /**
     * 我的：已参与活动
     */
    Page<DwOldActivity> findOldUserActivityById(Pageable pageable, String id);

    /**
     * 后台设置头像
     */
    JelUser setUserAvatar(SetUserAvatarDTO setUserAvatarDTO);

    /**
     * 设置头像
     */
//    JelUser uploadFile(String userId, MultipartFile file);
    JelUser portraitFile(String id, String url);


}
