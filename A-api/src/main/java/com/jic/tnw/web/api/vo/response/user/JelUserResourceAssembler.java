package com.jic.tnw.web.api.vo.response.user;

import com.jic.tnw.user.service.dto.user.JelUser;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class JelUserResourceAssembler extends ResourceAssemblerSupport<JelUser,UserResource> {
    public JelUserResourceAssembler(Class<?> controllerClass, Class<UserResource> resourceType) {
        super(controllerClass, resourceType);
    }
    @Override
    public UserResource toResource(JelUser entity) {
        UserResource userResource = new UserResource();
//        int id = Integer.parseInt(entity.getUser().getLuuserid());
        userResource.setUserId(entity.getUser().getLuuserid());
        userResource.setUserName(entity.getUser().getLuusername());
//        userResource.setTrueName(entity.getUser().getTruename());
        userResource.setRoles(entity.getUser().getLuorgid());
        userResource.setOrgIds(entity.getUser().getLucorpid());
        userResource.setOrgNames(entity.getUser().getOrganizationId());
        userResource.setLastLoginIp(entity.getUser().getLulastlogip());
        userResource.setLocked(entity.getUser().getLuactive());
        userResource.setLastLoginTime(entity.getUser().getLulastlogtime());
        userResource.setMobile(entity.getUser().getLumobile());
//        userResource.setCreateIp(entity.getUser().getCreatedIp());
        userResource.setCreateTime(entity.getUser().getLuregtime());
        userResource.setEmail(entity.getUser().getLuemail());
        userResource.setPostId(entity.getUser().getPositionId());
//        if (entity.getUser().getLockDeadline()!=null){
//            userResource.setLockedNumber(entity.getUser().getLockDeadline());
//
//        }
//        if (entity.getUserExtension()!=null){
//            if (entity.getUserExtension().getIdcard()!=null){
//                userResource.setIdcard(entity.getUserExtension().getIdcard());
//            }
//        }

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