package com.jic.tnw.web.api.vo.response.role;

import com.jic.tnw.user.service.dto.WurTree;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class WurTreeResourceAssembler extends ResourceAssemblerSupport<WurTree, WurTreeResource> {


    public WurTreeResourceAssembler(Class<?> controllerClass, Class<WurTreeResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public WurTreeResource toResource(WurTree entity) {

        WurTreeResource wurTreeResource = new WurTreeResource();
        to(entity, wurTreeResource);
        return wurTreeResource;
    }


    private WurTreeResource to(WurTree entity, WurTreeResource wtr) {

        wtr.setWurTreeId(entity.getId());
        wtr.setDescription(entity.getDescription());
        wtr.setWurCode(entity.getWurCode());
        wtr.setUrl(entity.getUrl());
        wtr.setType(entity.getType());
        wtr.setUpdatedTime(entity.getUpdatedTime());
        wtr.setUpdatedUserId(entity.getUpdatedUserId());
        wtr.setParentId(entity.getParentId());
        wtr.setChildrenNum(entity.getChildrenNum());
        wtr.setDepth(entity.getDepth());
        wtr.setSeq(entity.getSeq());
        wtr.setDescription(entity.getDescription());
        wtr.setCode(entity.getCode());
        wtr.setWurCode(entity.getWurCode());
        wtr.setCreatedTime(entity.getCreatedTime());
        wtr.setCreatedUserId(entity.getCreatedUserId());
        List<WurTree> list = entity.getChild();
        if (!CollectionUtils.isEmpty(list)) {
            WurTreeResource wtrChild;
            for (WurTree tree : list) {
                wtrChild = new WurTreeResource();
                wtr.addChild(to(tree, wtrChild));
            }
        }
//        Method method = null;
//        try {
//            method = OrgController.class.getMethod("getOrg", Integer.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Link link = linkTo(method, entity.getId()).withSelfRel();
//
//        wtr.add(link);

        return wtr;
    }


}
