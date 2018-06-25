package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.db.repository.*;
import com.jic.tnw.db.repository.impl.JooqRoleRepository;
import com.jic.tnw.user.service.dto.AddUserDTO;
import com.jic.tnw.user.service.dto.UserSetRoleGroup;
import com.jic.tnw.user.service.dto.user.EditUserInfoDTO;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.common.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * UserServiceImpl
 *
 * @author lee5hx, guotp
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);

    private final OrgRepository orgRepository;
    private final UserOrgRepository userOrgRepository;
    private final UserRepository userRepository;
    private final PostMemberRepository postMemberRepository;
    private final PostRepository postRepository;
    private final JooqRoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository,
                    OrgRepository orgRepository,
                    UserOrgRepository userOrgRepository,
                    PostMemberRepository postMemberRepository,
                    PostRepository postRepository,
                    JooqRoleRepository roleRepository,
                    UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
        this.userOrgRepository = userOrgRepository;
        this.postMemberRepository = postMemberRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String generateUserName() {
        return userRepository.generateUserName();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JelUser add(AddUserDTO addUserDTO) {
        User user = new User();
        user.setEmail(addUserDTO.getEmail());
        user.setUsername(addUserDTO.getUsername());
        user.setTruename(addUserDTO.getTruename());
        user.setPassword(addUserDTO.getPassword());
        user.setType(addUserDTO.getType());
        user.setPostId(Integer.valueOf(addUserDTO.getPostId()));
        List<String> codeList = new ArrayList<>();
        for (String code : addUserDTO.getRoles()) {
            Role role = roleRepository.findByCode(code);
            if (role == null) {
                throw new RoleCodeIdNotFoundException();
            }
            if (!code.equals("ROLE_USER")){
                codeList.add(code);
            }

        }
        codeList.add("ROLE_USER");
        String Roles = codeList.stream().collect(Collectors.joining(","));
        user.setRoles(Roles);
        user = userRepository.add(user);
        //更新岗位信息
        updatePostMemberByPostIDAndUserID(addUserDTO.getUid(), Integer.valueOf(addUserDTO.getPostId()));
        //更新组织机构
        JelUser jelUser = updateOrgByUserId(user.getId(), addUserDTO.getUid(), addUserDTO.getOrgIds());

        //Add UserProfile
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());

        userProfileRepository.add(userProfile);
        return jelUser;
    }


    @Override
    public User findByPrincipal(String principal) {
        return userRepository.findByPrincipal(principal);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "JelUser", key = "#id.toString()")
    public JelUser findById(Integer id) {
        JelUser jelUser = new JelUser();
        jelUser.setUser(userRepository.findById(id));
        jelUser.setUserExtension(userProfileRepository.findById(id));
        return jelUser;
    }

    @Override
    public Boolean existsPhoneNo(String phoneNo) {
        Boolean rt = false;
        User user = userRepository.findUserByMobile(phoneNo);
        if (user != null) {
            rt = true;
        }
        return rt;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<User> findTeachersByUserName(String userName){
        // SELECT user.* FROM user user INNER JOIN user_profile profile ON user.id = profile.id WHERE (user.roles LIKE '%%ROLE_TEACHER%%') AND (UPPER(user.nickname) LIKE '%%1%%') ORDER BY createdTime DESC LIMIT 10 OFFSET 0
        return userRepository.findTeachersByContainsUserName(userName);
    }

    @Override
    public Page<User> findWithPageable(Pageable pageable, Map<String, Object> conditionMap) {
        return userRepository.find(conditionMap, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CachePut(value = "JelUser", key = "#id.toString()")
    public JelUser updateOrgByUserId(Integer id, Integer uid, String[] orgIds) {

        JelUser user = findById(id);
        if (user == null) {
            throw new UserNotExistsException();
        }
        //删除原来的记录
        userOrgRepository.deleteByUserId(id);
        Org org;
        UserOrg userOrg;
        StringBuilder orgIdsSb = new StringBuilder("");
        StringBuilder orgCodesSb = new StringBuilder("");
        for (String orgId : orgIds) {
            org = orgRepository.findById(Integer.valueOf(orgId));
            if (org == null) {
                throw new OrgNotExistsException();
            } else {
                userOrg = new UserOrg();
                userOrg.setOrgId(org.getId());
                userOrg.setOrgCode(org.getOrgCode());
                userOrg.setUserId(id);
                userOrg.setCreatedTime(LocalDateTime.now());
                userOrg.setCreatedUserId(uid);
                userOrg.setLastUpdTime(LocalDateTime.now());
                userOrg.setLastUpdUserId(uid);
                userOrgRepository.add(userOrg);
            }
            orgIdsSb.append(org.getId());
            orgIdsSb.append("|");
            orgCodesSb.append(org.getOrgCode());
            orgCodesSb.append("|");
        }

        userRepository.updateOrgById(id, orgIdsSb.toString(), orgCodesSb.toString());
        LOGGER.info(String.format("updateOrgByUserId ids=%s code=%s ", orgIdsSb.toString(), orgCodesSb.toString()));
        return findById(id);
    }

    @Override
    @CachePut(value = "JelUser", key = "#uid.toString()")
    public JelUser updatePostID(Integer postid, Integer uid) {
        userRepository.updatePostById(uid, postid);
        return findById(uid);
    }

    @Override
    public void updateOrgByUserIds(String[] ids, Integer uid, String[] orgIds) {
        for (String id : ids) {
            updateOrgByUserId(Integer.valueOf(id), uid, orgIds);
        }
    }

    @Override
    @CachePut(value = "JelUser", key = "#uid.toString()")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePostByUserIds(String[] ids, Integer uid, Integer postId) {
        for (String id : ids) {
            updatePostMemberByPostIDAndUserID(Integer.valueOf(id), postId);
        }
    }


    public User updatePostMemberByPostIDAndUserID(Integer uid, Integer postId) {
        JelUser user = findById(Integer.valueOf(uid));
        if (user == null) {
            throw new UserNotExistsException();
        }
        if (postId != 0) {
            Post postIdForPost = postRepository.findById(postId);
            if (postIdForPost == null) {
                throw new PostNotFoundException();
            }
        }
        //删除之前的
        postMemberRepository.delete(uid);
        PostMember postMember = new PostMember();
        if (Integer.valueOf(postId) == 0) {

        } else {
            postMember.setPostId(Integer.valueOf(postId));
            postMember.setUserId(uid);
            postMember.setCreatedUserId(uid);
            postMember.setCreatedTime(LocalDateTime.now());
            postMember.setLastUpdUserId(uid);
            postMember.setLastUpdTime(LocalDateTime.now());
            postMemberRepository.add(postMember);
        }
        return updatePostID(Integer.valueOf(postId), uid).getUser();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateLoginSuccess(String principal, String ip) {
        LOGGER.debug(String.format("user.updateLoginSuccess principal=%s ip=%s", principal, ip));
        userRepository.updateLoginSuccess(principal, ip);

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User setPromotedTeacher(Integer id, Boolean promoted) {
        userRepository.updatePromoted(id,promoted);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User setPromotedSeq(Integer id, Integer seq) {
        userRepository.updatePromotedSeq(id,seq);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateLoginFailure(String principal) {
        LOGGER.debug(String.format("user.updateLoginFailure=%s", principal));
        userRepository.updateLoginFailure(principal);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    @CachePut(value = "JelUser", key = "#id.toString()")
    public JelUser updateUserByRoleId(UserSetRoleGroup userSetRoleGroup, Integer id) {
        JelUser userbyId = findById(id);
        if (userbyId.getUser() == null) {
            throw new UserNotExistsException();
        }
        List<String> codeList = new ArrayList<>();
        for (String code : userSetRoleGroup.getIds()) {
            Role role = roleRepository.findByCode(code);
            if (role == null) {
                throw new RoleCodeIdNotFoundException();
            }
            if (!code.equals("ROLE_USER")){
                codeList.add(code);
            }
        }
        codeList.add("ROLE_USER");
        String roles = codeList.stream().collect(Collectors.joining(","));
        User user = userRepository.updateRolesById(roles, id);
        return findById(id);
    }

    @Override
    public Long countByOrg(Integer orgId) {
        long resultCount = userRepository.countByOrg(orgId);
        return resultCount;
    }

    @Override
    public Long countByPost(Integer postId) {
        long resultCount = userRepository.countByPost(postId);
        return resultCount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void lock(Integer id) {
        userRepository.updateLockById(id, true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unlock(Integer id) {
        userRepository.updateLockById(id, false);
    }

    @Override
    public Integer countForLocked() {
        return userRepository.findByContainsLocked();
    }

    @Override
    public Long countByAllUser() {
        return userRepository.countByAllUser();
    }

    @Override
    @CachePut(value = "JelUser", key = "#id.toString()")
    public JelUser editUserName(String name, Integer id) {
        User userByName = userRepository.findByName(name);
        if (userByName != null) {
            throw new UserNameExistsException();
        }
        User userById = userRepository.findById(id);
        if (userById == null) {
            throw new UserNotExistsException();
        }
        userRepository.updateUserName(name, id);
        return findById(id);
    }

    @Override
    @CachePut(value = "JelUser", key = "#id.toString()")
    public JelUser editUserPwd(String pwd, Integer id) {
        User userById = userRepository.findById(id);
        if (userById == null) {
            throw new UserNotExistsException();
        }
        userRepository.updateUserPwd(pwd, id);
        return findById(id);
    }

    @Override
    @CachePut(value = "JelUser", key = "#editUserInfoDTO.getUserId().toString()")
    public JelUser updateUserProfile(EditUserInfoDTO editUserInfoDTO){
        JelUser jelUser = findById(editUserInfoDTO.getUserId());
        if (jelUser.getUser()==null){
            throw new UserNotExistsException();
        }
        UserProfile userProfileById = userProfileRepository.findById(editUserInfoDTO.getUserId());
        if (userProfileById == null) {
            throw new UserProfileNotexistException();
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(editUserInfoDTO.getUserId());
        userProfile.setAbout(editUserInfoDTO.getAbout());
        if (StringUtils.isEmpty(editUserInfoDTO.getIdcard())){
            userProfile.setIdcard("");
        }else {
            userProfile.setIdcard(editUserInfoDTO.getIdcard());
        }
        userProfile.setSignature(editUserInfoDTO.getSignature());
        if (StringUtils.isEmpty(editUserInfoDTO.getQq())){
            userProfile.setQq("");
        }else {
            userProfile.setQq(editUserInfoDTO.getQq());
        }
        if (StringUtils.isEmpty(editUserInfoDTO.getWeibo())){
            userProfile.setWeibo("");
        }else {
            userProfile.setWeibo(editUserInfoDTO.getWeibo());
        }
        if (StringUtils.isEmpty(editUserInfoDTO.getWeixin())){
            userProfile.setWeixin("");
        }else {
            userProfile.setWeixin(editUserInfoDTO.getWeixin());
        }
        if (StringUtils.isEmpty(editUserInfoDTO.getIam())){
            userProfile.setIam("");
        }else {
            userProfile.setIam(editUserInfoDTO.getIam());
        }
        if (StringUtils.isEmpty(editUserInfoDTO.getSite())){
            userProfile.setSite("");
        }else {
            userProfile.setSite(editUserInfoDTO.getSite());
        }

        userProfile.setGender(editUserInfoDTO.getGender());
        userProfileRepository.update(userProfile);

        if (editUserInfoDTO.getMobile()!=null){
            User user = userRepository.updateUserMobile(editUserInfoDTO.getMobile(),editUserInfoDTO.getTurename(),editUserInfoDTO.getUserId());

        }
        return findById(editUserInfoDTO.getUserId());
    }
    @Override
    @CachePut(value = "JelUser", key = "id.toString()")
    public JelUser setPostByPostIdUpdatePostMember(Integer postId,Integer id){
        JelUser jelUser = findById(id);
        if (jelUser == null) {
            throw new UserNotExistsException();
        }
        if (postId != 0) {
            Post postIdForPost = postRepository.findById(postId);
            if (postIdForPost == null) {
                throw new PostNotFoundException();
            }
        }
        //删除之前的
        postMemberRepository.delete(id);
        PostMember postMember = new PostMember();
        if (postId == 0) {

        } else {
            postMember.setPostId(postId);
            postMember.setUserId(id);
            postMember.setCreatedUserId(id);
            postMember.setCreatedTime(LocalDateTime.now());
            postMember.setLastUpdUserId(id);
            postMember.setLastUpdTime(LocalDateTime.now());
            postMemberRepository.add(postMember);
        }

        return updatePostID(postId, id);
    }
}
