package com.jic.tnw.web.api.vo.response.user;

import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.elearning.web.api.controller.v1.UserController;
import com.jic.elearning.web.api.vo.request.Tag.TagVerifyName;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author tp
 * @date 2018/4/12
 */
public class JelUserResourceAssembler extends ResourceAssemblerSupport<JelUser,UserResource> {
    public JelUserResourceAssembler(Class<?> controllerClass, Class<UserResource> resourceType) {
        super(controllerClass, resourceType);
    }
    @Override
    public UserResource toResource(JelUser entity) {
        UserResource userResource = new UserResource();
        userResource.setUserId(entity.getUser().getId());
        userResource.setUserName(entity.getUser().getUsername());
        userResource.setTrueName(entity.getUser().getTruename());
        userResource.setRoles(entity.getUser().getRoles());
        userResource.setOrgIds(entity.getUser().getOrgIds());
        userResource.setOrgNames(entity.getUser().getOrgCodes());
        userResource.setLastLoginIp(entity.getUser().getLoginIp());
        userResource.setLocked(entity.getUser().getLocked());
        userResource.setLastLoginTime(entity.getUser().getLoginTime());
        userResource.setMobile(entity.getUser().getMobile());
        userResource.setCreateIp(entity.getUser().getCreatedIp());
        userResource.setCreateTime(entity.getUser().getCreatedTime());
        userResource.setEmail(entity.getUser().getEmail());
        userResource.setPostId(entity.getUser().getPostId());
        if (entity.getUser().getLockDeadline()!=null){
            userResource.setLockedNumber(entity.getUser().getLockDeadline());

        }
        if (entity.getUserExtension()!=null){
            if (entity.getUserExtension().getIdcard()!=null){
                userResource.setIdcard(entity.getUserExtension().getIdcard());
            }
            userResource.setGender(entity.getUserExtension().getGender());
            userResource.setIam(entity.getUserExtension().getIam());
            userResource.setBirthday(entity.getUserExtension().getBirthday());
            userResource.setCity(entity.getUserExtension().getCity());
            userResource.setQq(entity.getUserExtension().getQq());
            userResource.setSignature(entity.getUserExtension().getSignature());
            userResource.setAbout(entity.getUserExtension().getAbout());
            userResource.setCompany(entity.getUserExtension().getCompany());
            userResource.setJob(entity.getUserExtension().getJob());
            userResource.setSchool(entity.getUserExtension().getSchool());
            userResource.setClass_(entity.getUserExtension().getClass_());
            userResource.setWeibo(entity.getUserExtension().getWeibo());
            userResource.setWeixin(entity.getUserExtension().getWeixin());
            userResource.setIsQqPublic(entity.getUserExtension().getIsQqPublic());
            userResource.setIsWeixinPublic(entity.getUserExtension().getIsWeixinPublic());
            userResource.setIsWeiboPublic(entity.getUserExtension().getIsWeiboPublic());
            userResource.setSite(entity.getUserExtension().getSite());
        }

//        try {
//            Link link = linkTo(
//                    UserController.class.getMethod("getUserById", Integer.class)
//                    , entity.getUser().getId()
//            ).withSelfRel();
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("setUserOrg", Integer.class, UserSetOrg.class, UserDetails.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_set_user_org");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("setUserPost", Integer.class, UserSetPost.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_set_user_post");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("setUserRoleGroup", Integer.class, UserSetRoleGroup.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_set_user_role_group");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("setLock", Integer.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_set_lock");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("setUnLock", Integer.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_set_unlock");
//            userResource.add(link);
//
//
//            link = linkTo(
//                    UserController.class.getMethod("editUserInfo", UserSetInfo.class, Integer.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_edit_user_info");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("editUserName", UserSetName.class, Integer.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_edit_username");
//            userResource.add(link);
//
//            link = linkTo(
//                    UserController.class.getMethod("editUserPassWord", UserSetPassWord.class, Integer.class)
//                    , null
//                    , entity.getUser().getId()
//            ).withRel("patch_edit_user_password");
//            userResource.add(link);
//
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        return userResource;
    }
}
