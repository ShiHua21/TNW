package com.jic.tnw.web.api.vo.response.org;

import com.jic.tnw.user.service.dto.OrgTree;
import com.jic.tnw.web.api.controller.v1.OrgController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class GetOrgTreeResourceAssembler extends ResourceAssemblerSupport<OrgTree, OrgTreeResource> {


    public GetOrgTreeResourceAssembler(Class<?> controllerClass, Class<OrgTreeResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public OrgTreeResource toResource(OrgTree entity) {
        OrgTreeResource otr = new OrgTreeResource();
        to(entity, otr);
        return otr;
    }

    private OrgTreeResource to(OrgTree entity, OrgTreeResource otr) {
        otr.setOrgId(entity.getOrgId());
        otr.setName(entity.getName());
        otr.setParentId(entity.getParentId());
        otr.setChildrenNum(entity.getChildrenNum());
        otr.setDepth(entity.getDepth());
        otr.setSeq(entity.getSeq());
        otr.setDescription(entity.getDescription());
        otr.setCode(entity.getCode());
        otr.setOrgCode(entity.getOrgCode());
        otr.setCreatedTime(entity.getCreatedTime());
        otr.setCreatedUserId(entity.getCreatedUserId());
        otr.setCreatedUserName(entity.getCreatedUserName());
        otr.setLastUpdTime(entity.getLastUpdTime());
        otr.setLastUpdUserId(entity.getLastUpdUserId());
        otr.setLastUpdTime(entity.getLastUpdTime());
        otr.setSyncId(entity.getSyncId());
        List<OrgTree> list = entity.getChild();
        if (!CollectionUtils.isEmpty(list)) {
            OrgTreeResource otrChild;
            for (OrgTree tree : list) {
                otrChild = new OrgTreeResource();
                otr.addChild(to(tree, otrChild));
            }
        }
        Method method = null;
        try {
            method = OrgController.class.getMethod("getOrg", Integer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Link link = linkTo(method, entity.getOrgId()).withSelfRel();

        otr.add(link);

        return otr;
    }


}
