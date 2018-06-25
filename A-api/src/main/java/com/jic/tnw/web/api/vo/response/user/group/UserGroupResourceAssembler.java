package com.jic.tnw.web.api.vo.response.user.group;

import com.jic.tnw.db.mysql.tables.pojos.UserGroup;
import com.jic.elearning.web.api.controller.v1.UserGroupController;
import com.jic.tnw.web.api.controller.v1.UserGroupController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class UserGroupResourceAssembler extends ResourceAssemblerSupport<UserGroup, UserGroupResource> {


    public UserGroupResourceAssembler(Class<?> controllerClass, Class<UserGroupResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public UserGroupResource toResource(UserGroup entity) {
        UserGroupResource resource = new UserGroupResource();
        resource.setUserGroupId(entity.getId());
        resource.setName(entity.getName());
        resource.setCode(entity.getCode());
        resource.setDescription(entity.getDescription());
        resource.setPeopleNumber(0);
        try {
            Link link = linkTo(
                    UserGroupController.class.getMethod("getUserGroup", Integer.class)
                    , entity.getId()
            ).withSelfRel();
            resource.add(link);

            link = linkTo(
                    UserGroupController.class.getMethod("getMember", Integer.class)
                    , entity.getId()
            ).withRel("get_list_member");
            resource.add(link);



        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
