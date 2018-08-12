package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.User;
import org.springframework.web.multipart.MultipartFile;


public interface UserRepository extends JooqRepository<User> {

    /**
     * 使用手机号查找用户
     */
    User findUserByMobile(String mobile);

    /**
     * 通过中主要凭证(username,email,mobile)查询用户
     */
    User findByPrincipal(String principal);

    /**
     * 更新登陆成功信息
     */
    void updateLoginSuccess(String principal, String ip);

    /**
     * 更新登陆失败信息
     */
    void updateLoginFailure(String principal);

    /**
     * 更新user密码
     */
    User oldUserPwd(String pwd, String id);

    /**
     * 更新user密码
     */
    User updateUserPwd(String pwd, String id);

    /**
     * 绑定手机i
     */
    User userSetMobile(String phoneNo, String id);

    /**
     * 设置用户头像
     */
    User uploadPortraitFile(String userId, MultipartFile file);

    /**
     * 设置用户头像
     */
    User portraitFile(String id, String url);
}
