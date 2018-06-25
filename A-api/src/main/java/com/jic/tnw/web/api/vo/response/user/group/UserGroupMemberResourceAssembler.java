package com.jic.tnw.web.api.vo.response.user.group;

import com.jic.tnw.db.mysql.tables.pojos.UserGroupMember;
import com.jic.elearning.web.api.controller.v1.UserGroupController;
import com.jic.tnw.web.api.controller.v1.UserGroupController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class UserGroupMemberResourceAssembler extends ResourceAssemblerSupport<UserGroupMember, UserGroupMemberResource> {


    public UserGroupMemberResourceAssembler(Class<?> controllerClass, Class<UserGroupMemberResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public UserGroupMemberResource toResource(UserGroupMember entity) {
        UserGroupMemberResource resource = new UserGroupMemberResource();
        resource.setUserGroupMemberId(entity.getId());
        resource.setMemberType(entity.getMemberType());
        resource.setGroupId(entity.getGroupId());
        resource.setMemberId(entity.getMemberId());
        resource.setPeopleNumber(0L);
        try {
            Link link = linkTo(
                    UserGroupController.class.getMethod("deleteMember", Integer.class, Integer.class),
                    entity.getGroupId(),
                    entity.getId()
            ).withSelfRel();
            resource.add(link);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
