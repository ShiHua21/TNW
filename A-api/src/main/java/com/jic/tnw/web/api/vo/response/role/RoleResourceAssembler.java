package com.jic.tnw.web.api.vo.response.role;

import com.jic.tnw.db.mysql.tables.pojos.Role;
import com.jic.elearning.web.api.controller.v1.RoleController;
import com.jic.tnw.web.api.controller.v1.RoleController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

    private Map<String, Integer> map;
    private List<String> ids;

    public RoleResourceAssembler(Class<?> controllerClass, Class<RoleResource> resourceType) {
        super(controllerClass, resourceType);
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    @Override

    public RoleResource toResource(Role entity) {

        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(entity.getId());
        roleResource.setName(entity.getName());
        roleResource.setCode(entity.getCode());
        roleResource.setCreatTime(entity.getCreatedTime());
        List<Integer> roles = new ArrayList<>();

        for (String code : ids) {
            Integer id = map.get(code);
            roles.add(id);
        }
        roleResource.setData(roles);
        try {
            Link link = linkTo(
                    RoleController.class.getMethod("getUserById", Integer.class)
                    , entity.getId()
            ).withSelfRel();
            roleResource.add(link);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return roleResource;
    }
}
